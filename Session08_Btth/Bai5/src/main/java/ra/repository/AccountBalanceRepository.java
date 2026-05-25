package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.AccountBalance;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, String> {
}