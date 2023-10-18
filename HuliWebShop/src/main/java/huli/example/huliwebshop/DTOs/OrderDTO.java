package huli.example.huliwebshop.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
  private Long orderId;
  private UserDTO userDTO;
  private String shippingAddress;
  private String paymentMethod;
  private BigDecimal totalPrice;
  private String orderStatus;
  private LocalDateTime orderDate;
  private List<OrderItemDTO> orderItems;

  public OrderDTO() {
  }

  public OrderDTO(
          Long orderId,
          UserDTO userDTO,
          String shippingAddress,
          String paymentMethod,
          BigDecimal totalPrice,
          String orderStatus,
          LocalDateTime orderDate,
          List<OrderItemDTO> orderItems
  ) {
    this.orderId = orderId;
    this.userDTO = userDTO;
    this.shippingAddress = shippingAddress;
    this.paymentMethod = paymentMethod;
    this.totalPrice = totalPrice;
    this.orderStatus = orderStatus;
    this.orderDate = orderDate;
    this.orderItems = orderItems;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public UserDTO getUserDTO() {
    return userDTO;
  }

  public void setUserDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
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

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public List<OrderItemDTO> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItemDTO> orderItems) {
    this.orderItems = orderItems;
  }
}





