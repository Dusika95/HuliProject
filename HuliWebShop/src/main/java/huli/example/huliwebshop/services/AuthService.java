package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.AuthenticationResponse;
import huli.example.huliwebshop.DTOs.UserLoginDTO;
import huli.example.huliwebshop.DTOs.UserLoginResponseDTO;
import huli.example.huliwebshop.DTOs.UserRegisterDTO;

public interface AuthService {

    UserLoginResponseDTO validateUser(UserLoginDTO userLoginDTO) throws Exception;

    AuthenticationResponse registerUser(UserRegisterDTO userRegisterDTO);
}
