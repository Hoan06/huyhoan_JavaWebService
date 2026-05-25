package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.WarehouseKeeper;

@Repository
public interface WarehouseKeeperRepository extends JpaRepository<WarehouseKeeper, Long> {}