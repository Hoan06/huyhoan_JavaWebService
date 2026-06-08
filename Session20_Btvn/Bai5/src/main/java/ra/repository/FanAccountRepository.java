package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.FanAccount;

import java.util.Optional;

public interface FanAccountRepository extends JpaRepository<FanAccount, Long> {
    Optional<FanAccount> findByEmail(String email);
}