package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.DTOs.CartItemUpdateDTO;
import huli.example.huliwebshop.DTOs.CartViewDTO;
import huli.example.huliwebshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/carts")
public class CartController {
  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping("/{userId}")
  public ResponseEntity<String> addToCart(@PathVariable Long userId, @RequestBody CartDTO cartDTO) {
    return cartService.addToCart(userId, cartDTO);
  }
  @DeleteMapping("/{userId}")
  public void clearCart(@PathVariable Long userId) {
    cartService.clearCart(userId);
  }

  @GetMapping("/{userId}")
  public CartViewDTO viewCart(@PathVariable Long userId) {
    return cartService.viewCart(userId);
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<String> updateCartItemQuantity(@PathVariable Long userId, @RequestBody CartItemUpdateDTO cartItemUpdateDTO) {
    return cartService.updateCartItemQuantity(userId, cartItemUpdateDTO);
  }

}
