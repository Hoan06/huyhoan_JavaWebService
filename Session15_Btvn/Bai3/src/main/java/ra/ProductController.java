package ra;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<String> getAllProducts() {
        return Arrays.asList("Laptop Thinkpad", "Mouse Logitech", "Keyboard Akko");
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to SmartRetail Hub!";
    }
}