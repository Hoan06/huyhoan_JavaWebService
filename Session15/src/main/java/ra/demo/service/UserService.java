package ra.demo.service;

import ra.demo.model.dto.request.UserDTO;
import ra.demo.model.entity.Users;

public interface UserService {
    Users registerUser(UserDTO userDTO);
}
