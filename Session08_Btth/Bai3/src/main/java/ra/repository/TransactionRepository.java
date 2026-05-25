package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Transaction;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionCode(String transactionCode);
}