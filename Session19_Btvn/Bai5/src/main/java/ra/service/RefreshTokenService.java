package ra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.entity.RefreshToken;
import ra.repository.RefreshTokenRepository;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(86400 * 7));
        refreshToken.setStatus("ACTIVE");
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyAndGetToken(String tokenStr) {
        RefreshToken token = refreshTokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("ERR_REFRESH_NOT_FOUND"));

        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("ERR_REFRESH_EXPIRED");
        }

        if ("REVOKED".equals(token.getStatus())) {
            throw new RuntimeException("ERR_REFRESH_REVOKED");
        }

        return token;
    }

    public void revokeToken(String tokenStr) {
        RefreshToken token = refreshTokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("ERR_REFRESH_NOT_FOUND"));
        token.setStatus("REVOKED");
        refreshTokenRepository.save(token);
    }
}