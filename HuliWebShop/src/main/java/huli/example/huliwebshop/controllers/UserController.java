package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public void registerUser(@RequestBody UserDTO userDTO) {
    userService.registerUser(userDTO);
  }

  @PostMapping("/login")
  public void loginUser(@RequestBody UserDTO userDTO) {
    userService.loginUser(userDTO);
  }
}
