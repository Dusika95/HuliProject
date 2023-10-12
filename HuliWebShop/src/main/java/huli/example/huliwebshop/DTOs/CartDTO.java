package huli.example.huliwebshop.DTOs;

public class CartDTO {
  private Long productId;
  private int quantity; // Add a quantity field

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity; // Getter for quantity
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity; // Setter for quantity
  }
}