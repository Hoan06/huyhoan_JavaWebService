package ra.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.demo.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
