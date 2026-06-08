package ra.data;

import ra.model.entity.*;
import ra.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        AppUser user = AppUser.builder()
                .username("huyhoan")
                .password(passwordEncoder.encode("hoan2026"))
                .email("hoan@gmail.com")
                .build();
        appUserRepository.save(user);

        Product p1 = Product.builder().name("Laptop ASUS ROG Strix").price(35000000.0).build();
        Product p2 = Product.builder().name("Chuột Gaming Logitech G502").price(1500000.0).build();
        Product p3 = Product.builder().name("Bàn phím cơ Akko 3098B").price(2200000.0).build();
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);

        PurchaseOrder order1 = PurchaseOrder.builder()
                .userId(user.getId()).status("COMPLETED").orderDate(LocalDateTime.now()).build();
        purchaseOrderRepository.save(order1);

        orderItemRepository.save(OrderItem.builder().orderId(order1.getId()).productId(p1.getId()).quantity(1).unitPrice(35000000.0).build());
        orderItemRepository.save(OrderItem.builder().orderId(order1.getId()).productId(p2.getId()).quantity(2).unitPrice(1500000.0).build());

        PurchaseOrder order2 = PurchaseOrder.builder()
                .userId(user.getId()).status("CANCELED").orderDate(LocalDateTime.now()).build();
        purchaseOrderRepository.save(order2);

        orderItemRepository.save(OrderItem.builder().orderId(order2.getId()).productId(p3.getId()).quantity(1).unitPrice(2200000.0).build());
    }
}