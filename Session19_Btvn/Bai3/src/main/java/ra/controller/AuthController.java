package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;
import ra.entity.RefreshToken;
import ra.service.RefreshTokenService;
import java.util.Map;

@Configuration
@EnableWebSecurity
class InlineSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader(value = "X-Device-Id", required = false) String deviceId,
                                   @RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (!"admin".equals(username) || !"password123".equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        Long mockUserId = 1L;
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(mockUserId, deviceId);

        return ResponseEntity.ok().body(Map.of(
                "accessToken", "mock-access-token-valid-for-15-minutes",
                "refreshToken", refreshToken.getToken(),
                "userId", mockUserId,
                "deviceId", refreshToken.getDeviceId()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "X-Device-Id", required = false) String deviceId,
                                    @RequestBody Map<String, Long> requestBody) {
        Long userId = requestBody.get("userId");
        refreshTokenService.logoutCurrentDevice(userId, deviceId);
        return ResponseEntity.ok().body(Map.of("message", "Logged out successfully from current device."));
    }

    @PostMapping("/logoutAllDevices")
    public ResponseEntity<?> logoutAllDevices(@RequestBody Map<String, Long> requestBody) {
        Long userId = requestBody.get("userId");
        refreshTokenService.logoutAllDevices(userId);
        return ResponseEntity.ok().body(Map.of("message", "Logged out successfully from all devices."));
    }
}