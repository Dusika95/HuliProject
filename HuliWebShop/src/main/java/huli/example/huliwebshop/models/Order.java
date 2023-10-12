package huli.example.huliwebshop.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> orderItems;

    private BigDecimal totalPrice;
    private LocalDateTime orderDate;

    private String shippingAddress;
    private String paymentMethod;
    private String orderStatus;

    public Order() {
      }

  public Order(Long id, User user, Map<Long, Integer> orderItems, BigDecimal totalPrice, LocalDateTime orderDate, String shippingAddress, String paymentMethod, String orderStatus) {
    this.id = id;
    this.user = user;
    this.orderItems = orderItems;
    this.totalPrice = totalPrice;
    this.orderDate = orderDate;
    this.shippingAddress = shippingAddress;
    this.paymentMethod = paymentMethod;
    this.orderStatus = orderStatus;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Map<Long, Integer> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(Map<Long, Integer> orderItems) {
    this.orderItems = orderItems;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
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

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }
}

