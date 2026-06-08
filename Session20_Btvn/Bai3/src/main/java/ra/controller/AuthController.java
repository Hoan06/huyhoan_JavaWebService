package ra.controller;

import ra.model.dto.request.LoginRequest;
import ra.model.dto.request.RefreshRequest;
import ra.model.dto.response.JwtResponse;
import ra.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        authService.logout(username);
        return ResponseEntity.ok("Đăng xuất thành công");
    }
}