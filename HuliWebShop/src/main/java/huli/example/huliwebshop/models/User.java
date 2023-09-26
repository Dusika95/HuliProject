package huli.example.huliwebshop.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String password;
  private String address;
  private String zipCode;
  private String city;
  private String role;


  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Cart cart;
  @OneToMany(
          mappedBy = "user",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<Comment> comments= new ArrayList<>();

  public User() {}

  public User(Long id, String name, String email, String password, String address, String zipCode, String city, String role, Cart cart, List<Comment> comments) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.address = address;
    this.zipCode = zipCode;
    this.city = city;
    this.role = role;
    this.cart = cart;
    this.comments = comments;
  }

  public User(String name, String email, String password, String address, String zipCode, String city, String role) {

    this.name = name;
    this.email = email;
    this.password = password;
    this.address = address;
    this.zipCode = zipCode;
    this.city = city;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
    return Collections.singletonList(authority);
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email; // need to return email here as there is no getEmail in userdetails (we use email to login not username)
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


  public void setPassword(String password) {
    this.password = password;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}

