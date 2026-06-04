package ra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.dto.request.ReviewRequest;
import ra.model.entity.Order;
import ra.model.entity.Product;
import ra.model.entity.Review;
import ra.model.entity.User;
import ra.repository.OrderRepository;
import ra.repository.ProductRepository;
import ra.repository.ReviewRepository;
import ra.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Review createReview(ReviewRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Order> userOrders = orderRepository.findByUser(user);
        boolean hasBought = userOrders.stream()
                .filter(order -> "COMPLETED".equalsIgnoreCase(order.getStatus()))
                .flatMap(order -> order.getOrderItems().stream())
                .anyMatch(item -> item.getProduct().getId().equals(product.getId()));

        if (!hasBought) {
            throw new RuntimeException("You can only review products you have successfully purchased");
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedDate(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return reviewRepository.findByProduct(product);
    }
}