package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {}
