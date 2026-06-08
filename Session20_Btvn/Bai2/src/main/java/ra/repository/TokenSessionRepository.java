package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.TokenSession;

import java.util.List;
import java.util.Optional;

public interface TokenSessionRepository extends JpaRepository<TokenSession, Long> {
    List<TokenSession> findByAccount_IdAndIsRevokedFalse(Long accountId);
    Optional<TokenSession> findByRefreshTokenValue(String token);
}