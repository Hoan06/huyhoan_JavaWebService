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

    public RefreshToken createRefreshToken(Long userId, String deviceId) {
        String finalDeviceId = (deviceId == null || deviceId.trim().isEmpty())
                ? UUID.randomUUID().toString()
                : deviceId;

        refreshTokenRepository.deleteByUserIdAndDeviceId(userId, finalDeviceId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(86400 * 7));
        refreshToken.setDeviceId(finalDeviceId);

        return refreshTokenRepository.save(refreshToken);
    }

    public void logoutCurrentDevice(Long userId, String deviceId) {
        refreshTokenRepository.deleteByUserIdAndDeviceId(userId, deviceId);
    }

    public void logoutAllDevices(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public void purgeExpiredTokens() {
        Instant now = Instant.now();
        int deletedCount = refreshTokenRepository.deleteExpiredTokens(now);
        System.out.println("Purged expired tokens count: " + deletedCount);
    }
}