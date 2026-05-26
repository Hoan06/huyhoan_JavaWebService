package ra;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String id);
}

