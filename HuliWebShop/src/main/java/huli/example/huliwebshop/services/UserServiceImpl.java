package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.UserDTO;
import huli.example.huliwebshop.DTOs.UserRegisterDTO;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final IUserRepository iUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(IUserRepository iUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserRegisterDTO userRegisterDTO) throws Exception {
        try {
            if (userRegisterDTO.getName() == null || userRegisterDTO.getName().isEmpty() ||
                    userRegisterDTO.getEmail() == null || userRegisterDTO.getEmail().isEmpty() ||
                    userRegisterDTO.getPassword() == null || userRegisterDTO.getPassword().isEmpty() ||
                    userRegisterDTO.getAddress() == null || userRegisterDTO.getAddress().isEmpty() ||
                    userRegisterDTO.getZipCode() == null || userRegisterDTO.getZipCode().isEmpty() ||
                    userRegisterDTO.getCity() == null || userRegisterDTO.getCity().isEmpty()) {
                throw new IllegalArgumentException("All fields are required for registration.");
            }

            User existingUser = iUserRepository.findByEmail(userRegisterDTO.getEmail());
            if (existingUser != null) {
                throw new RuntimeException("A user with this email already exists.");
            }
            String role="";
            String email = userRegisterDTO.getEmail();
            if (email.equals("admin@example.com")) {
                role="admin";
            } else {
                role="customer";
            }
            String hashedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
            User user = new User(userRegisterDTO.getName(), userRegisterDTO.getEmail(),
                    hashedPassword, userRegisterDTO.getAddress(), userRegisterDTO.getZipCode(), userRegisterDTO.getCity(), role);
            iUserRepository.save(user);

            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isEmailTaken(String email) {
        User existingUser = iUserRepository.findByEmail(email);
        return existingUser != null;
    }
    @Override
    public List<UserDTO> getAllUser() throws Exception {
        List<User> users = new ArrayList<>();
        users = iUserRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (!Objects.equals(users.get(i).getRole(), "admin")) {
                UserDTO userDTO = new UserDTO();
                userDTO.setName(users.get(i).getName());
                userDTO.setCity(users.get(i).getCity());
                userDTO.setAddress(users.get(i).getAddress());
                userDTO.setEmail(users.get(i).getEmail());
                userDTO.setZipCode(users.get(i).getZipCode());
                userDTOs.add(userDTO);
            }
        }
        if (userDTOs.isEmpty()) {
            throw new Exception("No user yet.");
        } else {
            return userDTOs;
        }
    }
    @Override
    public User deleteUserById(long id) throws Exception {
        Optional<User> user = iUserRepository.findById(id);
        if (!iUserRepository.findById(id).isPresent()) {
            throw new Exception("this id not exist");
        } else {
            if (iUserRepository.findById(id).get().getRole().equals("admin")) {
                throw new IllegalArgumentException("admin cannot be deleted");
            } else {
                iUserRepository.deleteById(id);
                return user.get();
            }
        }
    }

}



