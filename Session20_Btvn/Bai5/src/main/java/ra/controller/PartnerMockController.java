package ra.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.PartnerQrRequest;
import ra.model.dto.response.PartnerQrResponse;

@RestController
@RequestMapping("/api/mock/partner")
public class PartnerMockController {

    @PostMapping
    public ResponseEntity<PartnerQrResponse> mockQrGenerate(@RequestBody PartnerQrRequest request) {
        PartnerQrResponse response = new PartnerQrResponse("https://mock.com/qr/" + request.getOrderId() + ".png");
        return ResponseEntity.ok(response);
    }
}