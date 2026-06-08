package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.BookingOrder;

public interface BookingOrderRepository extends JpaRepository<BookingOrder, Long> {
}