package ra.service;

import org.springframework.data.domain.Page;
import ra.model.entity.Product;

public interface ProductService {
    Page<Product> getAlls(Integer page, Integer size , String sortBy);
    Product insert(Product product);
    Product update(Long proId , Product product);
    boolean delete(Long proId);
    Product findById(Long proId);
}
