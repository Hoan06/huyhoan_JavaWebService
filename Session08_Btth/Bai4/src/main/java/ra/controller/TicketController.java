package ra.controller;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ra.service.LessonService;

@RestController
@RequestMapping("/api/lessons")
@Validated
public class TicketController {

    private final LessonService lessonService;

    public TicketController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/watch")
    public ResponseEntity<String> watchLesson(
            @RequestHeader(value = "X-User", required = false) String userId,
            @RequestParam("lessonId") @Min(value = 1) Long lessonId) {

        String videoUrl = lessonService.watchLesson(lessonId, userId);
        return ResponseEntity.ok(videoUrl);
    }
}