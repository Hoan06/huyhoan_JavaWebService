package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.demo.model.entity.Product;
import ra.demo.repository.ProductRepository;
import ra.demo.service.ProductService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updatePartialProduct(Long proId, Product product) {
        Product productUpdate = productRepository.findById(proId).orElseThrow(() -> new NoSuchElementException("Không tồn tại sản phẩm có mã: " + product));
        if(product.getProName()!=null && !product.getProName().isEmpty()){
            productUpdate.setProName(product.getProName());
        }
        if(product.getProducer()!=null && !product.getProducer().isEmpty()){
            productUpdate.setProducer(product.getProducer());
        }
        if(product.getYearMaking()!=null && product.getYearMaking()>0){
            productUpdate.setYearMaking(product.getYearMaking());
        }
        if(product.getExpireDate()!=null){
            productUpdate.setExpireDate(product.getExpireDate());
        }

        return productRepository.save(productUpdate);
    }
}
