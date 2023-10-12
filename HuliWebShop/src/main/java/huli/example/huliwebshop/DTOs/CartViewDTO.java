package huli.example.huliwebshop.DTOs;

import java.util.HashMap;
import java.util.Map;

public class CartViewDTO {
  private Long id;
  private Map<String, CartItem> cartEntries = new HashMap<>();
  private double totalCartPrice;

  public CartViewDTO() {
  }

  public CartViewDTO(Long id, Map<String, CartItem> cartEntries, double totalCartPrice) {
    this.id = id;
    this.cartEntries = cartEntries;
    this.totalCartPrice = totalCartPrice;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Map<String, CartItem> getCartEntries() {
    return cartEntries;
  }

  public void setCartEntries(Map<String, CartItem> cartEntries) {
    this.cartEntries = cartEntries;
  }

  public double getTotalCartPrice() {
    return totalCartPrice;
  }

  public void setTotalCartPrice(double totalCartPrice) {
    this.totalCartPrice = totalCartPrice;
  }

  public static class CartItem {
    private int quantity;
    private double price;

    public CartItem(int quantity, double price) {
      this.quantity = quantity;
      this.price = price;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    public double getPrice() {
      return price;
    }

    public void setPrice(double price) {
      this.price = price;
    }
  }
}
