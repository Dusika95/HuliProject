/*package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.models.Cart;
import huli.example.huliwebshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping("/{userId}")
  public void addToCart(@PathVariable Long userId, @RequestBody CartDTO cartDTO) {
    cartService.addToCart(userId, cartDTO);
  }

  @GetMapping("/{userId}")
  public Cart viewCart(@PathVariable Long userId) {
    return cartService.viewCart(userId);
  }

  @DeleteMapping("/{userId}")
  public void clearCart(@PathVariable Long userId) {
    cartService.clearCart(userId);
  }
}*/
