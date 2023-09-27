package huli.example.huliwebshop.services;


import huli.example.huliwebshop.DTOs.UserLoginDTO;
import huli.example.huliwebshop.DTOs.UserLoginResponseDTO;

import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.IUserRepository;
import huli.example.huliwebshop.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthServiceImpl implements AuthService {

    private final IUserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(IUserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserLoginResponseDTO validateUser(UserLoginDTO userLoginDTO) throws Exception {
        // presence checks should be done on frontend, then there would bo no need for API calls just to present-check field inputs, duh...
        if (Objects.equals(userLoginDTO.getEmail(), "") || userLoginDTO.getEmail() == null) {
            throw new Exception("Email is required.");
        }

        if (Objects.equals(userLoginDTO.getPassword(), "") || userLoginDTO.getPassword() == null) {
            throw new Exception("Password is required.");
        }

        if (Objects.equals(userLoginDTO.getEmail(), "") || userLoginDTO.getEmail() == null && Objects.equals(userLoginDTO.getPassword(), "") || userLoginDTO.getPassword() == null) {
            throw new Exception("All fields are required.");
        }
        // need to authenticate the login credentials - this is all that is needed

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getEmail(),
                            userLoginDTO.getPassword()

                    )
            );
        } catch (Exception e) {
            // here the authentication manager and userdetails can throw their own exceptions, so we catch them here and throw our own new exception with custom message for the client
            System.out.println("Authentication failed: " + e.getMessage());
            throw new Exception("Email or password is incorrect.");
        }

        // if all checks out, and we pass authentication, we create a new user from the user returned from the repo
        // here I wanted to search by email "AND PASSWORD", but the password is hashed, so it will not find user by it - the method is implemented in the repo, but not used - needs few changes
        User verifiedUser = userRepository.findByEmail(userLoginDTO.getEmail());

        // we check if the returned user is null...
        // ...and also we can check if the "raw - plaintext" password from the loginDTO matches the encrypted password from the DB...
        if (verifiedUser == null || !passwordEncoder.matches(userLoginDTO.getPassword(), verifiedUser.getPassword())) {
            throw new Exception("Email or password do not match.");
        } else {
            // ...if all checks out then we create generate the token from the user data and return the token in the authentication response
            String token = jwtUtil.generateToken(verifiedUser);

//      return new AuthenticationResponse(token);
            UserLoginResponseDTO userResponse = new UserLoginResponseDTO(verifiedUser.getId(), verifiedUser.getName(), verifiedUser.getEmail(), verifiedUser.getRole(), token);

            return userResponse;
        }
    }
}
