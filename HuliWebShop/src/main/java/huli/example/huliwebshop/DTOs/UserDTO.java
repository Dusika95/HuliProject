package huli.example.huliwebshop.DTOs;

public class UserDTO {
  private String name;
  private String email;
  private String address;
  private String zipCode;
  private String city;

  public UserDTO(){
  }
  public UserDTO(String name, String email, String address, String zipCode, String city) {
    this.name = name;
    this.email = email;
    this.address = address;
    this.zipCode = zipCode;
    this.city = city;
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
}
