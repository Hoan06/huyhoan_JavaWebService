package ra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Product;

public interface ProductService {
    Product insertProdcut(Product product);
    Page<Product> getAllProducts(Integer page, Integer size);
    Product updateProdcut(Long proId,Product product);
}
