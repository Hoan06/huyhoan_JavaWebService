package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.ReviewRequest;
import ra.model.entity.Review;
import ra.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request, Authentication authentication) {
        String username = authentication.getName();
        Review review = reviewService.createReview(request, username);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long id) {
        List<Review> reviews = reviewService.getReviewsByProduct(id);
        return ResponseEntity.ok(reviews);
    }
}