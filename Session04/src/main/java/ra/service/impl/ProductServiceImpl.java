package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Product;
import ra.repository.ProductRepository;
import ra.service.ProductService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product insertProdcut(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updateProdcut(Long proId, Product product) {
        Product proUpdate = productRepository.findById(proId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm"));
        if (product.getProName() != null && product.getProName().length() > 0){
            proUpdate.setProName(product.getProName());
        }
        if (product.getProducer() != null && product.getProducer().length() > 0){
            proUpdate.setProducer(product.getProducer());
        }
        if (product.getYearMarking() != null && product.getYearMarking() > 0){
            proUpdate.setYearMarking(product.getYearMarking());
        }
        if (product.getExpireDate() != null){
            proUpdate.setExpireDate(product.getExpireDate());
        }
        return productRepository.save(proUpdate);
    }
}
