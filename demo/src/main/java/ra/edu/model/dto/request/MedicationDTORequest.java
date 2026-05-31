package ra.edu.model.dto.request;

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
public class MedicationDTORequest {
    @NotBlank(message = "Không được để trống tên thuốc")
    private String name;
    @NotBlank(message = "Không được để trống tên nhà sản xuất")
    private String manufacturer;
    @NotNull(message = "Không được để trống giá bán")
    @Min(value = 0,message = "Giá bán không được để số âm")
    private Double price;
    @NotNull(message = "Không được để trống trạng thái thuốc")
    private StatusType status;
    @NotNull(message = "Không được để trống trạng thái xóa")
    private Boolean isDelete;
}
