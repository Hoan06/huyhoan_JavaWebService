package ra.service;

import org.springframework.stereotype.Service;
import ra.model.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductService() {
        products.add(new Product(idGenerator.getAndIncrement(), "Mì Tôm Cyberpunk", 15.0, 50));
        products.add(new Product(idGenerator.getAndIncrement(), "Nước Tăng Lực Neon", 25.0, 30));
    }

    public List<Product> getAll() {
        return products;
    }

    public Product getById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product addProduct(Product product) {
        product.setId(idGenerator.getAndIncrement());
        products.add(product);
        return product;
    }

    public void deleteProduct(Long id) {
        Product product = getById(id);
        products.remove(product);
    }
}
