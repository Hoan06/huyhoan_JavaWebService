package ra.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.UserDTO;
import ra.model.dto.request.UserLogin;
import ra.model.dto.response.ApiDataResponse;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.User;
import ra.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<User>> register(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(new ApiDataResponse<>(
            true,
            "Đăng ký tài khoản thành công .",
            userService.registerUser(userDTO),
            null,
            HttpStatus.CREATED
        ) , HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<List<User>>> getUsers() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách tài khoản thành công",
                userService.getAll(),
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
}
