package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.TicketCategory;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
}