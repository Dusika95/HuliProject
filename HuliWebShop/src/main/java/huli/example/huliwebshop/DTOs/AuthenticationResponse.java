package huli.example.huliwebshop.DTOs;

public class AuthenticationResponse {
    private final String token;
    private String type;

    public AuthenticationResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}
