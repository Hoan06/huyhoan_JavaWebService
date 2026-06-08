package ra.service;

import ra.model.dto.request.RefreshTokenRequest;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.Token;

public interface TokenService {
    Token createRefreshToken(String username);
    Token verifyRefreshToken(String tokenRefresh);
    JWTResponse refreshToken(RefreshTokenRequest tokenRefresh);
}
