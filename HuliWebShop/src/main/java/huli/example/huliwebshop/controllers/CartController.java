package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.CartDTO;
import huli.example.huliwebshop.models.Cart;
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
  /*@PutMapping("/{id")
  public ResponseEntity editCart(@PathVariable Long id){

  }*/
}
/*    @PutMapping("/{id}")
    public ResponseEntity editProduct(@PathVariable Long id, @RequestBody ProductUpdateDTO productUpdateDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(productService.editProductById(id,productUpdateDTO));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }*/
