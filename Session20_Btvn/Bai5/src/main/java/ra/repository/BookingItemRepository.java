package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.BookingItem;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
}