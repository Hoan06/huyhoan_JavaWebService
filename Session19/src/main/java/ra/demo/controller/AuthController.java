package ra.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.demo.model.dto.request.RefreshTokenRequest;
import ra.demo.model.dto.request.UserDTO;
import ra.demo.model.dto.request.UserLogin;
import ra.demo.model.dto.response.ApiDataResponse;
import ra.demo.model.dto.response.JWTResponse;
import ra.demo.model.entity.Users;
import ra.demo.service.RefreshTokenService;
import ra.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<Users>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng ký tài khoản " + userDTO.getUsername() + " thành công",
                userService.registerUser(userDTO),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<Users>>> getUsers() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách tài khoản thành công",
                userService.getAllUsers(),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<JWTResponse>> login(@RequestBody UserLogin userLogin) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng nhập thành công",
                userService.login(userLogin),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiDataResponse<JWTResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cấp lại access token mới thành công",
                refreshTokenService.refreshToken(refreshTokenRequest),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
}
