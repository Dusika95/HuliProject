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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.Map;
import java.util.Optional;

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

  private static final int MAX_PRODUCT_QUANTITY = 5;

  @Override
  @Transactional
  public ResponseEntity<String> addToCart(Long userId, CartDTO cartDTO) {
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

    Map<Product, Integer> cartEntries = cart.getCartEntries();

    Long productId = cartDTO.getProductId();
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
  public CartViewDTO viewCart(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      throw new RuntimeException("User not found with ID: " + userId);
    }
    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      throw new RuntimeException("Cart not found for the user with ID: " + userId);
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

    return cartViewDTO;
  }

  @Override
  @Transactional
  public void clearCart(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      throw new RuntimeException("User not found with ID: " + userId);
    }
    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      throw new RuntimeException("Cart not found for the user with ID: " + userId);
    }

    Map<Product, Integer> cartEntries = cart.getCartEntries();

    for (Map.Entry<Product, Integer> cartEntry : cartEntries.entrySet()) {
      Product product = cartEntry.getKey();
      int quantityInCart = cartEntry.getValue();
      int originalQuantity = product.getQuantity();

      product.setQuantity(originalQuantity + quantityInCart);
    }

    cartEntries.clear();

    productRepository.saveAll(cartEntries.keySet());
    cartRepository.save(cart);
  }

  @Override
  @Transactional
  public ResponseEntity<String> updateCartItemQuantity(Long userId, CartItemUpdateDTO cartItemUpdateDTO) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      return ResponseEntity.badRequest().body("User not found with ID: " + userId);
    }
    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      return ResponseEntity.badRequest().body("Cart not found for the user with ID: " + userId);
    }

    Map<Product, Integer> cartEntries = cart.getCartEntries();
    Long productId = cartItemUpdateDTO.getProductId();
    int newQuantity = cartItemUpdateDTO.getQuantity();

    Optional<Product> productOptional = productRepository.findById(productId);
    if (productOptional.isPresent()) {
      Product product = productOptional.get();
      int currentCartItemQuantity = cartEntries.getOrDefault(product, 0);

      if (newQuantity == 0) {
        int originalQuantity = product.getQuantity();
        int newProductQuantity = originalQuantity + currentCartItemQuantity;
        product.setQuantity(newProductQuantity);

        cartEntries.remove(product);
      } else {
        int availableQuantity = product.getQuantity();
        int globalLimit = 5;
        int maxQuantity = Math.min(globalLimit, availableQuantity + currentCartItemQuantity);

        if (newQuantity >= 0 && newQuantity <= maxQuantity) {
          int quantityDifference = newQuantity - currentCartItemQuantity;
          int newProductQuantity = availableQuantity - quantityDifference;
          if (newProductQuantity < 0) {
            return ResponseEntity.badRequest().body("Product quantity cannot go below 0.");
          }
          product.setQuantity(newProductQuantity);

          cartEntries.put(product, newQuantity);
        } else {
          return ResponseEntity.badRequest().body("Requested quantity exceeds the limit or available quantity for product with ID: " + productId);
        }
      }
      cartRepository.save(cart);
      return ResponseEntity.ok("Cart item quantity updated successfully.");
    } else {
      return ResponseEntity.badRequest().body("Product not found with ID: " + productId);
    }
  }
}



