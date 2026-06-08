package ra.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ra.model.dto.request.CertificatePartnerRequest;
import ra.model.dto.response.CertificatePartnerResponse;

@FeignClient(
        name = "certificateClient",
        contextId = "elearningCertificateClient",
        url = "${api.certificate-service.url}"
)
public interface CertificateClient {

    @PostMapping
    CertificatePartnerResponse issueCertificate(@RequestBody CertificatePartnerRequest request);
}