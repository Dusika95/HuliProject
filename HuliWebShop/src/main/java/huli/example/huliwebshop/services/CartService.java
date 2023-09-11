package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.models.Cart;

public interface CartService {
  void addToCart(Long userId, CartDTO cartDTO);
  Cart viewCart(Long userId);
  void clearCart(Long userId);

}

