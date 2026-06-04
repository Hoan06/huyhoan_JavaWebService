package ra.controller;

import io.jsonwebtoken.Claims;
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
import ra.service.JwtService;
import ra.service.RefreshTokenService;
import java.util.Map;

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (!"admin".equals(username) || !"password123".equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        Long mockUserId = 101L;
        String accessToken = jwtService.generateAccessToken(username);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(mockUserId);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken(),
                "status", "AUTHENTICATED"
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String reqRefreshToken = request.get("refreshToken");
        try {
            RefreshToken token = refreshTokenService.verifyAndGetToken(reqRefreshToken);
            String newAccessToken = jwtService.generateAccessToken("admin");
            return ResponseEntity.ok(Map.of(
                    "accessToken", newAccessToken,
                    "refreshToken", token.getToken()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revoke(@RequestBody Map<String, String> request) {
        String reqRefreshToken = request.get("refreshToken");
        try {
            refreshTokenService.revokeToken(reqRefreshToken);
            return ResponseEntity.ok(Map.of("message", "Token revoked successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/hr/salary-data")
    public ResponseEntity<?> getSalaryData(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "ERR_HEADER_MISSING"));
        }
        String token = authHeader.substring(7);
        try {
            Claims claims = jwtService.validateAndExtractClaims(token);
            return ResponseEntity.ok(Map.of(
                    "subject", claims.getSubject(),
                    "baseSalary", "$5000",
                    "bonus", "$1500",
                    "status", "DATA_RETRIEVED"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}