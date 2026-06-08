package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.AuthSession;

import java.util.List;
import java.util.Optional;

public interface AuthSessionRepository extends JpaRepository<AuthSession, Long> {
    Optional<AuthSession> findByRefreshTokenValue(String token);
    List<AuthSession> findByFanIdAndIsRevokedFalse(Long fanId);
}