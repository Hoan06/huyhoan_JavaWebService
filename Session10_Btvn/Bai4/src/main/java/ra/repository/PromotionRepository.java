package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Promotion;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByCode(String code);
}