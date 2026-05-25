package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.InventoryLog;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, Long> {
}
