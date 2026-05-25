package ra.service;

import ra.model.dto.request.StockOutRequestDTO;
import ra.model.dto.request.StockRequestDTO;
import ra.model.entity.Product;

public interface ProductService {
    Product addProduct(StockRequestDTO productDTO);
    Product updateProduct(StockOutRequestDTO productDTO);
    String getInspect(String role);
    Boolean deleteProductById(Long id, String role);
}
