package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}