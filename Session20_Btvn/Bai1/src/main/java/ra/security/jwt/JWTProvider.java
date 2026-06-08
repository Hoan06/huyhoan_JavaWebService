package ra.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

    public String generateToken(String username){
        try {
            Date today = new Date();
            Date expiryDate = new Date(today.getTime() + jwtExpired);
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            return Jwts.builder()
                    .subject(username)
                    .issuedAt(today)
                    .expiration(expiryDate)
                    .signWith(key)
                    .compact();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

            return true;
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Hệ thống không hỗ trợ jwt", e);
        }catch (ExpiredJwtException e){
            throw new RuntimeException("Chuỗi jwt hết hạn", e);
        }catch (MalformedJwtException e){
            throw new RuntimeException("Chuỗi jwt sai định dạng", e);
        }catch (SignatureException e){
            throw new RuntimeException("Sai chữ kí JWT", e);
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Chuỗi JWT rỗng", e);
        }catch (JwtException e){
            throw new RuntimeException("Lỗi xác thực JWT", e);
        }
    }

    public String getUsernameFromToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
