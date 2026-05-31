package ra.edu.model.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.edu.model.entity.StatusType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicationDTOResponse {
    private Long id;
    private String name;
    private String manufacturer;
    private Double price;
    private StatusType status;
    private Boolean isDelete;
}
