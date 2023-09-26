package huli.example.huliwebshop.security.jwt;


import huli.example.huliwebshop.models.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {

    private static final Dotenv dotenv = Dotenv.load();

    // I included it because it seemed useful at the beginning, but did not get to use it - delete/change as needed
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // actual 256-bit secret key - do not delete - change as needed
    private static final String SECRET_KEY = dotenv.get("JWT_SECRET_KEY");

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBites);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractUserId(String token) {
        return new Long ((Integer)extractAllClaims(token).get("userId"));
    }

    public String getToken(String token) {
        return token.substring(7);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    // get the username from the token and compare it to what is in userDetails
    // also check if the token is not expired
    // remember we use getUsername() to return email!
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUsername(token);
        return (Objects.equals(userEmail, userDetails.getUsername()) && !isTokenExpired(token));
    }

    // claims map content here is what is returned as payload
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        return createToken(claims, user);
    }

    private String createToken(Map<String, Object> claims, User user) {
        return Jwts.builder()
                .setClaims(claims) // payload right here
                .setSubject(user.getEmail()) // should be unique string like username or email - change as needed
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(dotenv.get("JWT_EXPIRATION_IN")))) // this gets the current time then adds 1s (1000ms) and multiply with 60 to get 1min, then multiply with 60 to get 1h, and then the amount of time for the token to live (this case 12 hours)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // sign with the key which is returned by getSignInKey() method
                .compact();
    }
}


