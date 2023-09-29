package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }
  @GetMapping("/admin/all-users")
  public ResponseEntity getAllUsers() {
    try {
      return ResponseEntity.status(200).body(userService.getAllUser());
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }

  @DeleteMapping("/admin/users/{id}")
  public ResponseEntity deleteUser(@PathVariable Long id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }
}
