package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.model.dto.request.StockOutRequestDTO;
import ra.model.dto.request.StockRequestDTO;
import ra.model.entity.Product;
import ra.repository.ProductRepository;
import ra.service.ProductService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product addProduct(StockRequestDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSku(productDTO.getSku());
        product.setQuantity(productDTO.getQuantity());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(StockOutRequestDTO productDTO) {
        Product product = productRepository.findBySku(productDTO.getSku());
        if (product == null) {
            throw new NoSuchElementException("Không tìm thấy mã sku :  " + productDTO.getSku());
        }
        product.setQuantity(product.getQuantity() -  productDTO.getQuantity());
        return productRepository.save(product);
    }

    @Override
    public String getInspect(String role) {
        if (!"ADMIN".equals(role)) {
            return null;
        }
        Integer totalStock = 0;
        for (Product product : productRepository.findAll()) {
            totalStock += product.getQuantity();
        }
        Double totalPrice = 0.0;
        for (Product product : productRepository.findAll()) {
            totalPrice += product.getPrice();
        }
        return "Tổng số lượng trong kho : " + totalStock + " , tổng giá trị kho : " + totalPrice;
    }

    @Override
    public Boolean deleteProductById(Long id , String role) {
        productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy id " + id));
        productRepository.deleteById(id);
        return true;
    }
}
