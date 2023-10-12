package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.DTOs.CartItemUpdateDTO;
import huli.example.huliwebshop.DTOs.CartViewDTO;
import org.springframework.http.ResponseEntity;

public interface CartService {
  ResponseEntity<String> addToCart(Long userId, CartDTO cartDTO);
  CartViewDTO viewCart(Long userId);
  void clearCart(Long userId);
  ResponseEntity<String> updateCartItemQuantity(Long userId, CartItemUpdateDTO cartItemUpdateDTO);

}