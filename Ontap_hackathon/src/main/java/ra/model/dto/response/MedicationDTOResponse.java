package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.model.entity.MedicationStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicationDTOResponse {
    private Long id;
    private String name;
    private String manufacturer;
    private Double price;
    private MedicationStatus status;
    private Boolean is_deleted;
}
