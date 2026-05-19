package ra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ra.model.entity.Product;
import ra.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            productRepository.save(Product.builder().name("Điện lạnh").price(23000.0).build());
            productRepository.save(Product.builder().name("Tivi Sony").price(15000.0).build());
            productRepository.save(Product.builder().name("Máy giặt LG").price(12000.0).build());

        }
    }
}