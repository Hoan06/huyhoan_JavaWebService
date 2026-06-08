package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ra.model.dto.request.RefreshTokenRequest;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.Employee;
import ra.model.entity.Token;
import ra.model.entity.TokenType;
import ra.repository.EmployeeRepository;
import ra.repository.TokenRepository;
import ra.security.jwt.JWTProvider;
import ra.security.principal.CustomUserDetails;
import ra.service.TokenService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final EmployeeRepository employeeRepository;
    private final UserDetailsService userDetailsService;
    private final JWTProvider jwtProvider;

    @Value("${jwt-refresh-expired}")
    private Long jwtRefreshExpired;

    @Override
    public Token createRefreshToken(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        Token refreshToken = Token.builder()
                .username(username)
                .expired(false)
                .expiryDate(Instant.now().plusMillis(jwtRefreshExpired))
                .tokenValue(UUID.randomUUID().toString())
                .revoked(false)
                .tokenType(TokenType.REFRESH)
                .employee(employee)
                .build();
        return tokenRepository.save(refreshToken);
    }

    @Override
    public Token verifyRefreshToken(String tokenRefresh) {
        Token refreshToken = tokenRepository.findByTokenValueAndTokenTypeAndRevokedFalseAndExpiredFalse(tokenRefresh,TokenType.REFRESH)
                .orElseThrow(() -> new RuntimeException("Refresh token không hợp lệ"));
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshToken.setExpired(true);
            refreshToken.setRevoked(true);
            tokenRepository.save(refreshToken);
            throw new RuntimeException("Refresh token đã hết hạn");
        }
        if (refreshToken.isRevoked()){
            throw new RuntimeException("Refresh token đã bị thu hồi");
        }
        return refreshToken;
    }

    @Override
    public JWTResponse refreshToken(RefreshTokenRequest tokenRefresh) {
        String token = tokenRefresh.getRefreshToken();
        Token refreshToken = tokenRepository.findByTokenValueAndTokenType(token,TokenType.REFRESH);
        if (refreshToken == null) {
            throw new RuntimeException("Không tồn tại refresh token này");
        }

        verifyRefreshToken(refreshToken.getTokenValue());

        CustomUserDetails userDetails =  (CustomUserDetails) userDetailsService.loadUserByUsername(refreshToken.getUsername());
        String newAccessToken = jwtProvider.generateToken(userDetails.getUsername());

        return JWTResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getTokenValue())
                .build();
    }
}
