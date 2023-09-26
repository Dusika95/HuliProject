package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final IUserRepository iuserRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(IUserRepository iuserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.iuserRepository = iuserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserDTO userDTO) throws Exception {
        try {
            if (userDTO.getName() == null || userDTO.getName().isEmpty() ||
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
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            User user = new User(userDTO.getName(), userDTO.getEmail(),
                    hashedPassword, userDTO.getAddress(), userDTO.getZipCode(), userDTO.getCity(), "user");
            iuserRepository.save(user);

            return user;
        } catch (Exception e) {
            return null;
        }
    }

  /*@Override
  public User loginUser(UserDTO userDTO) {
    User user = iuserRepository.findByEmail(userDTO.getEmail());

    if (user != null && user.getPassword().equals(userDTO.getPassword())) {
      return user;
    }
    return null;
  }*/

    @Override
    public boolean isEmailTaken(String email) {
        User existingUser = iuserRepository.findByEmail(email);
        return existingUser != null;
    }
}



