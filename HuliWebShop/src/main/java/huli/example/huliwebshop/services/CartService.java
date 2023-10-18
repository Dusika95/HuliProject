package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.DTOs.CartItemUpdateDTO;
import huli.example.huliwebshop.DTOs.CartViewDTO;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface CartService {
  ResponseEntity<String> addToCart(CartDTO cartDTO, Principal principal);
  ResponseEntity<CartViewDTO> viewCart(Principal principal);
  ResponseEntity<String> clearCart(Principal principal);
  ResponseEntity<String> updateCartItemQuantity(CartItemUpdateDTO cartItemUpdateDTO, Principal principal);
}