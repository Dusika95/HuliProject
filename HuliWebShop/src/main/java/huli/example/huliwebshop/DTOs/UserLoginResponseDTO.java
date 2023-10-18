package huli.example.huliwebshop.DTOs;

public class UserLoginResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String jwtToken;

    public UserLoginResponseDTO(Long id, String name, String email, String role, String jwtToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.jwtToken = jwtToken;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
