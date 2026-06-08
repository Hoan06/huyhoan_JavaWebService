package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByUserId(Long userId);
}