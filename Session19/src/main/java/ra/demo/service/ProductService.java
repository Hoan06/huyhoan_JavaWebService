package ra.demo.service;

import org.springframework.data.domain.Page;
import ra.demo.model.entity.Product;

public interface ProductService {
    Product insertProduct(Product product);

    Page<Product> getAllProducts(Integer page, Integer pageSize);

    Product updatePartialProduct(Long proId, Product product);
}
