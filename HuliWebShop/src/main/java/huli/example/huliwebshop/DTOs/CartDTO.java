package huli.example.huliwebshop.DTOs;

import java.util.Set;

public class CartDTO {
  private Set<Long> productIds;

  public Set<Long> getProductIds() {
    return productIds;
  }

  public void setProductIds(Set<Long> productIds) {
    this.productIds = productIds;
  }
}

