package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.EmployeeDTO;
import ra.model.dto.request.LoginDTO;
import ra.model.dto.request.RefreshTokenRequest;
import ra.model.dto.response.ApiDataResponse;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.Employee;
import ra.model.entity.Token;
import ra.repository.EmployeeRepository;
import ra.repository.TokenRepository;
import ra.service.EmployeeService;
import ra.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final EmployeeService employeeService;
    private final TokenService tokenService;
    private final EmployeeRepository employeeRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<?>> register(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.register(employeeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiDataResponse.builder()
                        .success(true)
                        .message("Đăng ký thành công")
                        .data(employee)
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<?>> login(@RequestBody LoginDTO loginDTO) {
        JWTResponse jwtResponse = employeeService.login(loginDTO);

        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .success(true)
                        .message("Đăng nhập thành công")
                        .data(jwtResponse)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiDataResponse<?>> refreshToken(@RequestBody RefreshTokenRequest request) {
        JWTResponse jwtResponse = tokenService.refreshToken(request);

        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .success(true)
                        .message("Cấp lại access token thành công")
                        .data(jwtResponse)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiDataResponse<?>> logout(Authentication authentication) {
        String username = authentication.getName();

        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        List<Token> validTokens = tokenRepository
                .findAllByEmployee_IdAndRevokedFalseAndExpiredFalse(employee.getId())
                .stream()
                .peek(token -> {
                    token.setRevoked(true);
                    token.setExpired(true);
                })
                .toList();

        tokenRepository.saveAll(validTokens);
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                ApiDataResponse.builder()
                        .success(true)
                        .message("Đăng xuất thành công")
                        .data(null)
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}