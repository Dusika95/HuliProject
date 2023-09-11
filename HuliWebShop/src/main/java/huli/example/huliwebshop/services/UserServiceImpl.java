package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final IUserRepository iuserRepository;

  @Autowired
  public UserServiceImpl(IUserRepository iuserRepository) {
    this.iuserRepository = iuserRepository;
  }

  @Override
  public void registerUser(UserDTO userDTO) {
    if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty() ||
            userDTO.getLastName() == null || userDTO.getLastName().isEmpty() ||
            userDTO.getEmail() == null || userDTO.getEmail().isEmpty() ||
            userDTO.getPassword() == null || userDTO.getPassword().isEmpty() ||
            userDTO.getAddress() == null || userDTO.getAddress().isEmpty() ||
            userDTO.getZipCode() == null || userDTO.getZipCode().isEmpty() ||
            userDTO.getCity() == null || userDTO.getCity().isEmpty()) {
      throw new IllegalArgumentException("All fields are required for registration.");
    }

    User existingUser = iuserRepository.findByEmail(userDTO.getEmail());
    if (existingUser != null) {
      throw new RuntimeException("A user with this email already exists.");
    }

    User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getPassword(), userDTO.getAddress(), userDTO.getZipCode(), userDTO.getCity());
    iuserRepository.save(user);
  }

  @Override
  public User loginUser(UserDTO userDTO) {
    User user = iuserRepository.findByEmail(userDTO.getEmail());

    if (user != null && user.getPassword().equals(userDTO.getPassword())) {
      return user;
    }
    return null;
  }
}



