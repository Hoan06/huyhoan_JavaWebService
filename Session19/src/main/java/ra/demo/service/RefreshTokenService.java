package ra.demo.service;

import ra.demo.model.dto.request.RefreshTokenRequest;
import ra.demo.model.dto.response.JWTResponse;
import ra.demo.model.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);
    RefreshToken verifyExpiration(RefreshToken token);
    JWTResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
