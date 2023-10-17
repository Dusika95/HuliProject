package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.DTOs.CartItemUpdateDTO;
import huli.example.huliwebshop.DTOs.CartViewDTO;
import huli.example.huliwebshop.models.Cart;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.ICartRepository;
import huli.example.huliwebshop.repository.IUserRepository;
import huli.example.huliwebshop.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.security.Principal;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

  private final ICartRepository cartRepository;
  private final IUserRepository userRepository;
  private final IProductRepository productRepository;

  @Autowired
  public CartServiceImpl(ICartRepository cartRepository, IUserRepository userRepository, IProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @Override
  @Transactional
  public ResponseEntity<String> addToCart(CartDTO cartDTO, Principal principal) {
    String authenticatedUserEmail = principal.getName();

    User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail);
    Long userId = authenticatedUser.getId();

    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      return ResponseEntity.badRequest().body("User not found with ID: " + userId);
    }

    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      cart = new Cart();
      cart.setUser(user);
    }

    if (!authenticatedUserEmail.equals(user.getEmail())) {
      return ResponseEntity.badRequest().body("You can only add items to your own cart.");
    }

    Map<Product, Integer> cartEntries = cart.getCartEntries();

    Long productId = cartDTO.getProductId();
    if (productId == null) {
      return ResponseEntity.badRequest().body("Invalid request. 'productId' must be provided.");
    }
    Optional<Product> productOptional = productRepository.findById(productId);

    if (productOptional.isPresent()) {
      Product product = productOptional.get();
      int availableQuantity = product.getQuantity();

      if (availableQuantity > 0) {
        if (cartEntries.containsKey(product)) {
          int currentQuantity = cartEntries.get(product);
          if (currentQuantity + 1 > 5) {
            return ResponseEntity.badRequest().body("Quantity exceeds the limit (5) for product with ID: " + productId);
          }
        }

        product.setQuantity(availableQuantity - 1);

        int quantity = cartEntries.getOrDefault(product, 0);
        cartEntries.put(product, quantity + 1);

        cart.setLastModified(new Date());

        cartRepository.save(cart);

        return ResponseEntity.ok("Product added to the cart successfully.");
      } else {
        return ResponseEntity.badRequest().body("Product with ID " + productId + " is out of stock.");
      }
    } else {
      return ResponseEntity.badRequest().body("Product not found with ID: " + productId);
    }
  }


  @Override
  @Transactional
  public ResponseEntity<CartViewDTO> viewCart(Principal principal) {
    String authenticatedUserEmail = principal.getName();

    User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail);

    if (authenticatedUser == null) {
      CartViewDTO errorCartView = new CartViewDTO();
      errorCartView.setErrorMessage("User not found.");
      return ResponseEntity.badRequest().body(errorCartView);
    }

    Optional<User> userOptional = userRepository.findById(authenticatedUser.getId());
    if (!userOptional.isPresent()) {
      CartViewDTO errorCartView = new CartViewDTO();
      errorCartView.setErrorMessage("User not found with ID: " + authenticatedUser.getId());
      return ResponseEntity.badRequest().body(errorCartView);
    }

    User user = userOptional.get();

    if (!authenticatedUserEmail.equals(user.getEmail())) {
      CartViewDTO errorCartView = new CartViewDTO();
      errorCartView.setErrorMessage("You can only view your own cart.");
      return ResponseEntity.badRequest().body(errorCartView);
    }

    Cart cart = user.getCart();
    if (cart == null) {
      CartViewDTO errorCartView = new CartViewDTO();
      errorCartView.setErrorMessage("Cart not found for the user.");
      return ResponseEntity.badRequest().body(errorCartView);
    }

    CartViewDTO cartViewDTO = new CartViewDTO();
    cartViewDTO.setId(cart.getId());

    Map<String, CartViewDTO.CartItem> cartEntries = cartViewDTO.getCartEntries();
    double totalCartPrice = 0.0;

    for (Map.Entry<Product, Integer> entry : cart.getCartEntries().entrySet()) {
      Product product = entry.getKey();
      String productName = product.getName();
      int quantity = entry.getValue();
      double price = product.getPrice();

      double totalPrice = quantity * price;

      totalCartPrice += totalPrice;

      cartEntries.put(productName, new CartViewDTO.CartItem(quantity, totalPrice));
    }

    cartViewDTO.setTotalCartPrice(totalCartPrice);

    return ResponseEntity.ok(cartViewDTO);
  }

  @Override
  @Transactional
  public ResponseEntity<String> clearCart(Principal principal) {
    String authenticatedUserEmail = principal.getName();

    User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail);

    if (authenticatedUser == null) {
      return ResponseEntity.badRequest().body("User not found.");
    }

    Optional<User> userOptional = userRepository.findById(authenticatedUser.getId());
    if (!userOptional.isPresent()) {
      return ResponseEntity.badRequest().body("User not found with ID: " + authenticatedUser.getId());
    }

    User user = userOptional.get();

    if (!authenticatedUserEmail.equals(user.getEmail())) {
      return ResponseEntity.badRequest().body("You can only clear your own cart.");
    }

    Cart cart = user.getCart();
    if (cart == null) {
      return ResponseEntity.ok("Cart is already empty.");
    }

    Map<Product, Integer> cartEntries = cart.getCartEntries();

    for (Map.Entry<Product, Integer> cartEntry : cartEntries.entrySet()) {
      Product product = cartEntry.getKey();
      int quantityInCart = cartEntry.getValue();
      int originalQuantity = product.getQuantity();

      product.setQuantity(originalQuantity + quantityInCart);
    }

    cartEntries.clear();

    cart.setLastModified(new Date());

    productRepository.saveAll(cartEntries.keySet());
    cartRepository.save(cart);

    return ResponseEntity.ok("Cart cleared successfully.");
  }


  @Override
  @Transactional
  public ResponseEntity<String> updateCartItemQuantity(CartItemUpdateDTO cartItemUpdateDTO, Principal principal) {
    String authenticatedUserEmail = principal.getName();

    User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail);

    if (authenticatedUser == null) {
      return ResponseEntity.badRequest().body("User not found.");
    }

    Optional<User> userOptional = userRepository.findById(authenticatedUser.getId());
    if (!userOptional.isPresent()) {
      return ResponseEntity.badRequest().body("User not found with ID: " + authenticatedUser.getId());
    }

    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      return ResponseEntity.badRequest().body("Cart not found for the user.");
    }

    if (!authenticatedUserEmail.equals(user.getEmail())) {
      return ResponseEntity.badRequest().body("You can only update items in your own cart.");
    }

    if (cartItemUpdateDTO.getProductId() == null || cartItemUpdateDTO.getQuantity() == null) {
      return ResponseEntity.badRequest().body("Both 'productId' and 'quantity' must be provided in the request.");
    }

    Map<Product, Integer> cartEntries = cart.getCartEntries();
    Long productId = cartItemUpdateDTO.getProductId();
    int newQuantity = cartItemUpdateDTO.getQuantity();

    Optional<Product> productOptional = productRepository.findById(productId);
    if (productOptional.isPresent()) {
      Product product = productOptional.get();
      int currentCartItemQuantity = cartEntries.getOrDefault(product, 0);

      if (currentCartItemQuantity < 1) {
        return ResponseEntity.badRequest().body("There isn't any of that item in the cart. You can only update the quantity of an item if there's already at least 1 in the cart.");
      }

      if (newQuantity < 0) {
        return ResponseEntity.badRequest().body("Quantity cannot be negative.");
      } else if (newQuantity == 0) {
        int originalQuantity = product.getQuantity();
        int newProductQuantity = originalQuantity + currentCartItemQuantity;
        product.setQuantity(newProductQuantity);

        cartEntries.remove(product);

        cartRepository.save(cart);

        return ResponseEntity.ok("Item removed from the cart.");
      } else {
        int availableQuantity = product.getQuantity();
        int globalLimit = 5;
        int maxQuantity = Math.min(globalLimit, availableQuantity + currentCartItemQuantity);

        if (newQuantity <= maxQuantity) {
          int quantityDifference = newQuantity - currentCartItemQuantity;
          int newProductQuantity = availableQuantity - quantityDifference;

          if (newProductQuantity < 0) {
            return ResponseEntity.badRequest().body("Product quantity cannot go below 0.");
          }

          product.setQuantity(newProductQuantity);

          cartEntries.put(product, newQuantity);

          cart.setLastModified(new Date());

          cartRepository.save(cart);

          return ResponseEntity.ok("Cart item quantity updated successfully.");
        } else {
          return ResponseEntity.badRequest().body("Requested quantity exceeds the limit or available quantity for product with ID: " + productId);
        }
      }
    } else {
      return ResponseEntity.badRequest().body("Product not found with ID: " + productId);
    }
  }

  @Scheduled(fixedRate = 1200000)
  @Transactional
  public void clearInactiveCarts() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, -1200000);
    Date thresholdDate = cal.getTime();

    List<Cart> inactiveCarts = cartRepository.findInactiveCarts(thresholdDate);

    for (Cart cart : inactiveCarts) {
      Map<Product, Integer> cartEntries = cart.getCartEntries();

      for (Map.Entry<Product, Integer> entry : cartEntries.entrySet()) {
        Product product = entry.getKey();
        int quantityInCart = entry.getValue();
        int originalQuantity = product.getQuantity();
        product.setQuantity(originalQuantity + quantityInCart);
      }

      cartEntries.clear();
      cart.setLastModified(new Date());
    }

    cartRepository.saveAll(inactiveCarts);
  }
}



