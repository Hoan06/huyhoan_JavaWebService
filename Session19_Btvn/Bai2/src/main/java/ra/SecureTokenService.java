package ra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class SecureTokenService {

    private final long ACCESS_TOKEN_EXPIRATION_MINUTES = 15;

    public SecretKey getSigningKey() {
        String jwtSecret = System.getenv("JWT_SECRET_KEY");
        if (jwtSecret == null || jwtSecret.getBytes(StandardCharsets.UTF_8).length < 32) {
            jwtSecret = "defaultSuperSecretKeyForJWTHS256MustBeAtLeast32BytesLong";
        }
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(ACCESS_TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES);
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SecureTokenService service = new SecureTokenService();
        String username = "testUser";
        String badSecretKey = "AnEasilyGuessedKeyThatIsDifferentButLongEnoughToPass";

        String validToken = service.generateAccessToken(username);
        System.out.println("Valid Token: " + validToken);
        System.out.println("Valid Token status: " + service.validateToken(validToken));

        System.out.println("\n--- Attacker attempts with wrong key ---");
        SecretKey attackerKey = Keys.hmacShaKeyFor(badSecretKey.getBytes(StandardCharsets.UTF_8));
        String forgedToken = Jwts.builder()
                .subject("admin")
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(attackerKey)
                .compact();
        System.out.println("Forged Admin Token: " + forgedToken);
        System.out.println("Validation of Forged Token: " + service.validateToken(forgedToken));

        System.out.println("\n--- Testing Token Expiration Simulation ---");
        Instant now = Instant.now();
        Instant quickExpiry = now.plus(2, ChronoUnit.SECONDS);
        String shortLivedToken = Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(quickExpiry))
                .signWith(service.getSigningKey())
                .compact();

        Claims claims = Jwts.parser()
                .verifyWith(service.getSigningKey())
                .build()
                .parseSignedClaims(shortLivedToken)
                .getPayload();

        long durationMs = claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
        System.out.println("Token lifespan configured: " + durationMs + " ms");
        System.out.println("Initial token status: " + service.validateToken(shortLivedToken));

        System.out.println("Waiting for token to expire...");
        Thread.sleep(3000);
        System.out.println("Status after waiting: " + service.validateToken(shortLivedToken));
    }
}