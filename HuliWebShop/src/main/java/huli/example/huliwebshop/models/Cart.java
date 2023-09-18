package huli.example.huliwebshop.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String asdasd;

 /* @OneToMany(mappedBy = "cart")
  private Set<CartProduct> cartProducts = new HashSet<>();*/
  /*
  * @OneToMany(mappedBy = "cart")
private Set<CartProduct> cartProducts = new HashSet<>();*/

  public Cart() {
  }

  public Cart(Long id, User user/*, Set<CartProduct> cartProducts*/) {
    this.id = id;
    this.user = user;
    //this.cartProducts = cartProducts;
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

  /*public Set<CartProduct> getCartProducts() {
    return cartProducts;
  }

  public void setCartProducts(Set<CartProduct> cartProducts) {
    this.cartProducts = cartProducts;
  }*/
}

