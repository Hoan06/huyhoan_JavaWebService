package ra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.CertificatePartnerRequest;
import ra.model.dto.response.CertificatePartnerResponse;

@RestController
@RequestMapping("/api/mock/certificate")
public class CertificateMockController {

    @PostMapping
    public ResponseEntity<CertificatePartnerResponse> mockIssue(@RequestBody CertificatePartnerRequest request) {
        String generatedUrl = "https://mock-certificate-provider.com/download/"
                + request.getStudentName().replace(" ", "-") + "-"
                + request.getCourseTitle().replace(" ", "-") + ".pdf";
        return ResponseEntity.ok(new CertificatePartnerResponse(generatedUrl));
    }
}