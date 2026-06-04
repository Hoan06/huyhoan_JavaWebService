package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ra.demo.model.dto.request.RefreshTokenRequest;
import ra.demo.model.dto.response.JWTResponse;
import ra.demo.model.entity.RefreshToken;
import ra.demo.repository.RefreshTokenRepository;
import ra.demo.security.jwt.JWTProvider;
import ra.demo.security.principal.CustomUserDetails;
import ra.demo.service.RefreshTokenService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;
    private final JWTProvider jwtProvider;

    @Value("${jwt-refresh-expired}")
    private Long jwtRefreshExpired;

    @Override
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .username(username)
                .expiryDate(Instant.now().plusMillis(jwtRefreshExpired))
                .refreshToken(UUID.randomUUID().toString())
                .revoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
            throw new RuntimeException("Refresh token đã hết hạn");
        }
        if (token.isRevoked()) {
            throw new RuntimeException("Refresh token đã bị thu hồi");
        }
        return token;
    }

    @Override
    public JWTResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String token = refreshTokenRequest.getRefreshToken();
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token).orElseThrow(() -> new RuntimeException("Không tồn tại refresh token này"));

        verifyExpiration(refreshToken);

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(refreshToken.getUsername());
        String newAccessToken = jwtProvider.generateToken(userDetails.getUsername());

        return JWTResponse.builder()
                .username(userDetails.getUsername())
                .fullName(userDetails.getFullName())
                .email(userDetails.getEmail())
                .enabled(userDetails.getEnabled())
                .authorities(userDetails.getAuthorities())
                .token(newAccessToken)
                .build();
    }
}
