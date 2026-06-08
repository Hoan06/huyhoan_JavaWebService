package ra.model.dto.request;

import lombok.Data;

@Data
public class PartnerQrRequest {
    private Long orderId;
    private String fanEmail;
}