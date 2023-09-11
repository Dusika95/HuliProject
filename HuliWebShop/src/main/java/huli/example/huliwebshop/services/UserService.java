package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.models.User;

public interface UserService {
  void registerUser(UserDTO userDTO);
  User loginUser(UserDTO userDTO);
  boolean isEmailTaken(String email);
}

