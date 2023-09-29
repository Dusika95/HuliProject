package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.DTOs.UserLoginDTO;
import huli.example.huliwebshop.DTOs.UserRegisterDTO;
import huli.example.huliwebshop.DTOs.UserReturnDTO;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.services.AuthService;
import huli.example.huliwebshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody(required = false) UserRegisterDTO userRegisterDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            List<String> errorMessages = new ArrayList<>();

            errors.forEach(error -> errorMessages.add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errorMessages);
        }

        if (userService.isEmailTaken(userRegisterDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken.");
        }

        User registeredUser = userService.registerUser(userRegisterDTO);

        UserReturnDTO returnDTO = new UserReturnDTO(registeredUser.getId(), registeredUser.getEmail(), registeredUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(returnDTO);
    }


    @PostMapping("/login")
    public ResponseEntity validateUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            return ResponseEntity.ok().body(authService.validateUser(userLoginDTO));
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
