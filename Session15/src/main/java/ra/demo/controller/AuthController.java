package ra.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.demo.model.dto.request.UserDTO;
import ra.demo.model.dto.response.ApiDataResponse;
import ra.demo.model.entity.Users;
import ra.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<Users>> registerUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng ký tài khoản " + userDTO.getUsername() + " thành công",
                userService.registerUser(userDTO),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }
}
