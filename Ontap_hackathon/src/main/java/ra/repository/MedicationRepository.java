package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Page<Medication> findByNameContaining(String name, Pageable pageable);
    Page<Medication> findByManufacturerContaining(String manufacturer, Pageable pageable);
    Page<Medication> findByNameContainingAndManufacturerContaining(String name, String manufacturer, Pageable pageable);
}
