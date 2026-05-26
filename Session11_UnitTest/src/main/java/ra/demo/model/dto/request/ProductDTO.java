package ra.demo.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ra.demo.custom_validation.CategoryNotFound;
import ra.demo.custom_validation.YearMakingValidation;
import ra.demo.model.entity.Category;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    @NotEmpty(message = "Không được để trống tên sản phẩm")
    private String proName;
    @NotEmpty(message = "Không được để trống nhà sản xuất")
    private String producer;
    @NotNull(message = "Không được để trống năm sản xuất")
    @YearMakingValidation
    private Integer yearMaking;
    @NotNull(message = "Không được để trống ngày sản xuất")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;
    @NotNull(message = "Không được để trống danh mục sản phẩm")
    @CategoryNotFound
    private Category category;
}
