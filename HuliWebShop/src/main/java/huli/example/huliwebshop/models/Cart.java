package huli.example.huliwebshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @ElementCollection
  @CollectionTable(name = "cart_entries", joinColumns = @JoinColumn(name = "cart_id"))
  @MapKeyJoinColumn(name = "product_id")
  @Column(name = "quantity")
  private Map<Product, Integer> cartEntries = new HashMap<>();

  public Cart() {
  }

  public Cart(Long id, User user, Map<Product, Integer> cartEntries) {
    this.id = id;
    this.user = user;
    this.cartEntries = cartEntries;
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

  public Map<Product, Integer> getCartEntries() {
    return cartEntries;
  }

  public void setCartEntries(Map<Product, Integer> cartEntries) {
    this.cartEntries = cartEntries;
  }
}

