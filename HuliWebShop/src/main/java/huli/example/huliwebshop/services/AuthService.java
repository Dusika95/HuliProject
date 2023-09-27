package huli.example.huliwebshop.services;


import huli.example.huliwebshop.DTOs.UserLoginDTO;
import huli.example.huliwebshop.DTOs.UserLoginResponseDTO;


public interface AuthService {

    UserLoginResponseDTO validateUser(UserLoginDTO userLoginDTO) throws Exception;

}
