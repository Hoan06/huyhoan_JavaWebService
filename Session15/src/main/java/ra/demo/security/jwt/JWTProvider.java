package ra.demo.security.jwt;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTProvider {
    @Value("${jwt-secret}")
    private String jwtSecret;
    @Value("${jwt-expired}")
    private Long jwtExpired;
    @Value("${jwt-refresh-expired}")
    private Long jwtRefreshExpired;


    public String generateToken(String username){
        try {
            Date today =  new Date();
            Date expireJWT = new Date(today.getTime() + jwtExpired);
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            return Jwts.builder()
                    .subject(username)
                    .issuedAt(today)
                    .expiration(expireJWT)
                    .signWith(key)
                    .compact();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
