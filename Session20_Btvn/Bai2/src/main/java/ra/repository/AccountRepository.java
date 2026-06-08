package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> { Optional<Account> findByUsername(String username); }