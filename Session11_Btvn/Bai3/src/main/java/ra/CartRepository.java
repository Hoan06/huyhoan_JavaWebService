package ra;

import java.util.Optional;

public interface CartRepository {
    Optional<ShoppingCart> findByUserId(String userId);
    ShoppingCart save(ShoppingCart cart);
}
