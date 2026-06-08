package ra.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.RefreshToken;
import ra.model.entity.User;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    Optional<RefreshToken> findByUser(User user);
}