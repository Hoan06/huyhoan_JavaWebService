package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.service.ApplicationService;

@RestController
@RequestMapping("/api/v1/aop-services")
@RequiredArgsConstructor
public class MyController {
    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<String> doProcessing() {
        String result = applicationService.method1()
                + "\n"
                + applicationService.method2();

        return ResponseEntity.ok(result);
    }
}
