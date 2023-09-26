package huli.example.huliwebshop.DTOs;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

    @NotBlank(message = "Name is required.")
    private String name;
    @NotEmpty(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;
    @NotEmpty(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String password;
    @NotBlank(message = "Address is required.")
    private String address;
    @NotBlank(message = "Zipcode is required.")
    private String zipCode;
    @NotBlank(message = "City is required.")
    private String city;

    public UserRegisterDTO() {}

    public UserRegisterDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserRegisterDTO(String name, String email, String password, String address, String zipCode, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
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
}
