package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.StockOrder;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
}