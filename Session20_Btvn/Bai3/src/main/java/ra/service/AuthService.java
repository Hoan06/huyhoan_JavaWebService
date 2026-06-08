package ra.service;

import ra.jwt.JwtProvider;
import ra.model.dto.request.LoginRequest;
import ra.model.dto.request.RefreshRequest;
import ra.model.dto.response.JwtResponse;
import ra.model.entity.AppUser;
import ra.model.entity.UserToken;
import ra.repository.AppUserRepository;
import ra.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final UserTokenRepository userTokenRepository;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Tài khoản hoặc mật khẩu không đúng"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không đúng");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        String refreshToken = jwtProvider.generateRefreshToken(userDetails);

        UserToken token = UserToken.builder()
                .refreshToken(refreshToken)
                .isRevoked(false)
                .isExpired(false)
                .userId(user.getId())
                .build();
        userTokenRepository.save(token);

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refresh(RefreshRequest request) {
        UserToken token = userTokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh Token không hợp lệ"));

        if (token.isRevoked() || token.isExpired()) {
            throw new RuntimeException("Refresh Token đã bị hủy hoặc hết hạn");
        }

        String username = jwtProvider.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtProvider.isTokenValid(request.getRefreshToken(), userDetails)) {
            throw new RuntimeException("Token không hợp lệ");
        }

        String newAccessToken = jwtProvider.generateAccessToken(userDetails);
        return new JwtResponse(newAccessToken, request.getRefreshToken());
    }

    public void logout(String username) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        List<UserToken> tokens = userTokenRepository.findByUserIdAndIsRevokedFalse(user.getId());

        tokens.stream().forEach(t -> {
            t.setRevoked(true);
            userTokenRepository.save(t);
        });

        SecurityContextHolder.clearContext();
    }
}