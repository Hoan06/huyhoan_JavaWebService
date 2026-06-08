package ra.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ra.model.dto.request.PartnerQrRequest;
import ra.model.dto.response.PartnerQrResponse;

@FeignClient(name = "qrGeneratorClient", url = "${api.qr-service.url}")
public interface QrGeneratorClient {

    @PostMapping
    PartnerQrResponse generateQrCode(@RequestBody PartnerQrRequest request);
}
