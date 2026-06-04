package ra.service;

import ra.model.dto.request.UserDTO;
import ra.model.dto.request.UserLogin;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(UserDTO userDTO);
    JWTResponse login(UserLogin userLogin);
    List<User> getAll();
}
