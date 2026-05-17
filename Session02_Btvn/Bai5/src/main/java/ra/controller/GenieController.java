package ra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.exception.WishBadRequestException;
import ra.exception.WishForbiddenException;
import ra.model.entity.WishLog;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/genie")
public class GenieController {

    private final AtomicInteger wishCounter = new AtomicInteger(0);
    private final int MAX_WISHES = 3;

    private final List<WishLog> wishHistory = new CopyOnWriteArrayList<>();

    private final List<String> wisdomList = Arrays.asList(
            "Hãy code bằng cái tâm, bug sẽ tự khắc rời xa bạn.",
            "Muốn đi nhanh hãy đi một mình, muốn đi xa hãy viết Unit Test đầy đủ.",
            "Kho báu lớn nhất đời người là khả năng tự học và đọc tài liệu tiếng Anh."
    );

    private void checkWishLimit(String wishType, String details) {
        if (wishCounter.get() >= MAX_WISHES) {
            wishHistory.add(new WishLog(wishType, details, "REJECTED", "Thần đèn đã hết lượt ước!"));
            throw new WishForbiddenException("Thần đèn đã hết lượt ước! Bạn chỉ có tối đa 3 điều ước.");
        }
    }

    @PostMapping("/wishes/career")
    public ResponseEntity<Map<String, String>> wishForCareer(@RequestBody Map<String, String> requestBody) {
        String jobTitle = requestBody.get("jobTitle");

        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            throw new WishBadRequestException("Điều ước không hợp lệ! Bạn phải nêu rõ tên công việc mong muốn ('jobTitle').");
        }

        if (jobTitle.equalsIgnoreCase("Tỷ phú không làm gì cả")) {
            wishHistory.add(new WishLog ("Career Wish", jobTitle, "REJECTED", "Ước muốn vi phạm quy tắc lao động!"));
            throw new WishForbiddenException("Ước rằng bạn giàu có vô căn cứ là một điều ước không hợp lệ, thần đèn chỉ có thể giúp bạn tìm việc tốt hơn thôi!");
        }

        checkWishLimit("Career Wish", "Job: " + jobTitle);

        wishCounter.incrementAndGet();
        wishHistory.add(new WishLog("Career Wish", "Job: " + jobTitle, "SUCCESS", "Thần đèn chúc bạn sớm trở thành một " + jobTitle + " xuất sắc!"));

        return new ResponseEntity<>(Map.of(
                "message", "Bùm! Bạn đã được ban phước lành để trở thành một " + jobTitle + " tương lai.",
                "wishesUsed", String.valueOf(wishCounter.get())
        ), HttpStatus.CREATED);
    }

    @PutMapping("/wishes/appearance")
    public ResponseEntity<Map<String, String>> wishForAppearance(@RequestBody Map<String, String> requestBody) {
        String height = requestBody.get("height");
        String style = requestBody.get("style");

        if (height == null || style == null) {
            throw new WishBadRequestException("Yêu cầu thiếu thông tin chiều cao ('height') hoặc phong cách ('style').");
        }

        checkWishLimit("Appearance Wish", "Height: " + height + ", Style: " + style);

        wishCounter.incrementAndGet();
        wishHistory.add(new WishLog("Appearance Wish", "Height: " + height + ", Style: " + style, "SUCCESS", "Đã đổi ngoại hình thành công."));

        return ResponseEntity.ok(Map.of(
                "message", "Cơ thể bạn đang thay đổi! Chiều cao mới: " + height + ", Phong cách: " + style,
                "wishesUsed", String.valueOf(wishCounter.get())
        ));
    }

    @GetMapping("/wishes/wisdom")
    public ResponseEntity<Map<String, String>> wishForWisdom() {
        checkWishLimit("Wisdom Wish", "Requested advice");

        wishCounter.incrementAndGet();
        String randomWisdom = wisdomList.get(new Random().nextInt(wisdomList.size()));

        wishHistory.add(new WishLog("Wisdom Wish", "Received wisdom", "SUCCESS", "Đã ban lời khuyên."));

        return ResponseEntity.ok(Map.of(
                "wisdom", randomWisdom,
                "wishesUsed", String.valueOf(wishCounter.get())
        ));
    }

    @GetMapping("/wishes/history")
    public ResponseEntity<List<WishLog>> getWishHistory() {
        return ResponseEntity.ok(wishHistory);
    }
}