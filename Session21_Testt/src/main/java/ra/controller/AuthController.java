package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ra.model.dto.req.LoginRequest;
import ra.model.dto.req.TokenRefreshRequest;
import ra.model.dto.res.JwtRes;
import ra.model.dto.res.TokenRefreshResponse;
import ra.model.entity.RefreshToken;
import ra.model.entity.User;

import ra.security.jwt.JwtTokenProvider;
import ra.security.principle.UserPrinciple;
import ra.service.RefreshTokenService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtRes> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateToken(userPrinciple);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userPrinciple.getId());

        return ResponseEntity.ok(JwtRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .fullName(userPrinciple.getFullName())
                .role(userPrinciple.getAuthorities().iterator().next().getAuthority())
                .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtTokenProvider.generateToken(UserPrinciple.build(user));
                    return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, request.getRefreshToken()));
                })
                .orElseThrow(() -> new RuntimeException("Refresh Token không hợp lệ!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrinciple) {
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            refreshTokenService.deleteByUserId(userPrinciple.getId());
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Đăng xuất thành công, Refresh Token đã bị hủy bỏ hoàn toàn!");
        }
        return ResponseEntity.badRequest().body("Yêu cầu không hợp lệ!");
    }
}