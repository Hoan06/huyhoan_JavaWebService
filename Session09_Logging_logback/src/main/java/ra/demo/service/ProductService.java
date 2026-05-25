package ra.demo.service;

import org.springframework.data.domain.Page;
import ra.demo.model.dto.request.ProductDTO;
import ra.demo.model.entity.Product;

public interface ProductService {
    Product insertProduct(ProductDTO productDTO);

    Page<Product> getAllProducts(Integer page, Integer pageSize);

    Product updatePartialProduct(Long proId, Product product);
}
