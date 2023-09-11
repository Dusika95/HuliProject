package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.models.Cart;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.ICartRepository;
import huli.example.huliwebshop.repository.IUserRepository;
import huli.example.huliwebshop.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
  private final ICartRepository icartRepository;
  private final IUserRepository iuserRepository;
  private final IProductRepository iProductRepository;

  @Autowired
  public CartServiceImpl(ICartRepository icartRepository, IUserRepository iuserRepository, IProductRepository iProductRepository) {
    this.icartRepository = icartRepository;
    this.iuserRepository = iuserRepository;
    this.iProductRepository = iProductRepository;
  }

  @Override
  public void addToCart(Long userId, CartDTO cartDTO) {
    Optional<User> userOptional = iuserRepository.findById(userId);
    if (!userOptional.isPresent()) {
      throw new RuntimeException("User not found with ID: " + userId);
    }
    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      cart = new Cart();
      cart.setUser(user);
    }

    cart.getProducts().clear();

    Set<Product> productsToAdd = new HashSet<>();
    for (Long productId : cartDTO.getProductIds()) {
      Optional<Product> productOptional = iProductRepository.findById(productId);
      if (productOptional.isPresent()) {
        productsToAdd.add(productOptional.get());
      }
    }

    cart.setProducts(productsToAdd);

    icartRepository.save(cart);
  }

  @Override
  public Cart viewCart(Long userId) {
    Optional<User> userOptional = iuserRepository.findById(userId);
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
  public void clearCart(Long userId) {
    Optional<User> userOptional = iuserRepository.findById(userId);
    if (!userOptional.isPresent()) {
      throw new RuntimeException("User not found with ID: " + userId);
    }
    User user = userOptional.get();

    Cart cart = user.getCart();
    if (cart == null) {
      throw new RuntimeException("Cart not found for the user with ID: " + userId);
    }

    cart.getProducts().clear();

    icartRepository.save(cart);
  }

}

