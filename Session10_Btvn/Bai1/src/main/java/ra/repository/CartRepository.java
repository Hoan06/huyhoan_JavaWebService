package ra.repository;

import org.springframework.stereotype.Repository;
import ra.model.entity.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class CartRepository {
    private final List<CartItem> dbMemory = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Optional<CartItem> findByUserIdAndProductId(String userId, String productId) {
        return dbMemory.stream()
                .filter(item -> item.getUserId().equals(userId) && item.getProductId().equals(productId))
                .findFirst();
    }

    public List<CartItem> findByUserId(String userId) {
        return dbMemory.stream()
                .filter(item -> item.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public CartItem save(CartItem item) {
        if (item.getId() == null) {
            item.setId(idGenerator.getAndIncrement());
            dbMemory.add(item);
        } else {
            dbMemory.stream()
                    .filter(i -> i.getId().equals(item.getId()))
                    .forEach(i -> i.setQuantity(item.getQuantity()));
        }
        return item;
    }

    public boolean deleteById(Long id) {
        return dbMemory.removeIf(item -> item.getId().equals(id));
    }

    public Optional<CartItem> findById(Long id) {
        return dbMemory.stream().filter(item -> item.getId().equals(id)).findFirst();
    }
}