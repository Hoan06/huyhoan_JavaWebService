package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.response.CertificatePartnerResponse;
import ra.model.dto.response.ProgressResponse;
import ra.service.StudyService;

@RestController
@RequestMapping("/api/elearning/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/my-progress")
    public ResponseEntity<ProgressResponse> getMyProgress() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(studyService.getMyProgress(email));
    }

    @PostMapping("/{courseId}/claim-certificate")
    public ResponseEntity<CertificatePartnerResponse> claimCertificate(@PathVariable Long courseId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(studyService.claimCertificate(courseId, email));
    }
}