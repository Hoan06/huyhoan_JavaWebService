package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.UserToken;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByRefreshToken(String token);
    List<UserToken> findByUserIdAndIsRevokedFalse(Long userId);
}
