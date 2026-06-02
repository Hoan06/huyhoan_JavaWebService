package ra.demo.service;

import ra.demo.model.dto.request.UserDTO;
import ra.demo.model.dto.request.UserLogin;
import ra.demo.model.dto.response.JWTResponse;
import ra.demo.model.entity.Users;

import java.util.List;

public interface UserService {
    Users registerUser(UserDTO userDTO);
    JWTResponse login(UserLogin userLogin);
    List<Users> getAllUsers();
}
