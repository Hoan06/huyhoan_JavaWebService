package ra.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.demo.model.entity.RefreshToken;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    // Xóa tất cả token của một user (khi logout all)
    void deleteByUsername(String username);
    // Lấy tất cả token chưa revoke của user
    List<RefreshToken> findByUsername(String username);
}
