package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.DTOs.CartItemUpdateDTO;
import huli.example.huliwebshop.DTOs.CartViewDTO;
import huli.example.huliwebshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/carts")
public class CartController {
  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping
  public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO, Principal principal) {
    return cartService.addToCart(cartDTO, principal);
  }

  @DeleteMapping
  public ResponseEntity<String> clearCart(Principal principal) {
    return cartService.clearCart(principal);
  }

  @GetMapping
  public ResponseEntity<CartViewDTO> viewCart(Principal principal) {
    return cartService.viewCart(principal);
  }

  @PatchMapping
  public ResponseEntity<String> updateCartItemQuantity(@RequestBody CartItemUpdateDTO cartItemUpdateDTO, Principal principal) {
    return cartService.updateCartItemQuantity(cartItemUpdateDTO, principal);
  }
}
