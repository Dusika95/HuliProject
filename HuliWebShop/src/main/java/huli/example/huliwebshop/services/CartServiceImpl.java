package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.models.Cart;
import huli.example.huliwebshop.models.CartProduct;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.ICartRepository;
import huli.example.huliwebshop.repository.IUserRepository;
import huli.example.huliwebshop.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
  public void addToCart(Long userId, CartDTO cartDTO) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      throw new RuntimeException("User not found with ID: " + userId);
    }
    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      cart = new Cart();
      cart.setUser(user);
    }

    Set<CartProduct> cartProducts = new HashSet<>();
    for (Long productId : cartDTO.getProductIds()) {
      Optional<Product> productOptional = productRepository.findById(productId);
      if (productOptional.isPresent()) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(productOptional.get());
        cartProducts.add(cartProduct);
      }
    }

    cart.setCartProducts(cartProducts);

    cartRepository.save(cart);
  }

  @Override
  public Cart viewCart(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      throw new RuntimeException("User not found with ID: " + userId);
    }
    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      throw new RuntimeException("Cart not found for the user with ID: " + userId);
    }

    return cart;
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

    cart.getCartProducts().clear();

    cartRepository.save(cart);
  }
}
