package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Product> getAlls(Integer page, Integer size , String sortBy) {
        Sort sort = Sort.by("price").ascending();
        if ("desc".equalsIgnoreCase(sortBy)) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size,sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product insert(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long proId, Product product) {
        productRepository.findById(proId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy id : " + proId));
        product.setProId(proId);
        return productRepository.save(product);
    }

    @Override
    public boolean delete(Long proId) {
        productRepository.findById(proId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy id : " + proId));
        productRepository.deleteById(proId);
        return true;
    }

    @Override
    public Product findById(Long proId) {
        Product product = productRepository.findById(proId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy id : " + proId));
        return product;
    }
}
