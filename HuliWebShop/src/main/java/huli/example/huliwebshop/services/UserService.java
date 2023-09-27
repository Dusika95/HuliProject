package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.DTOs.UserRegisterDTO;
import huli.example.huliwebshop.models.User;

import java.util.List;

public interface UserService {
  User registerUser(UserRegisterDTO userRegisterDTO) throws Exception;
  boolean isEmailTaken(String email);
  List<UserDTO> getAllUser() throws Exception;
  User deleteUserById(long id) throws Exception;
}

