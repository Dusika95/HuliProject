package huli.example.huliwebshop.DTOs;

import java.math.BigDecimal;
import java.util.Set;

public class OrderDTO {
    private Long userId;
    private Set<Long> productIds;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private String paymentMethod;


    public OrderDTO() {
    }

    public OrderDTO(Long userId, Set<Long> productIds, BigDecimal totalPrice, String shippingAddress, String paymentMethod) {
        this.userId = userId;
        this.productIds = productIds;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

