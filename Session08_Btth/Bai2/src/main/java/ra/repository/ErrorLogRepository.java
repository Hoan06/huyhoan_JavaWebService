package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.ErrorLog;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}