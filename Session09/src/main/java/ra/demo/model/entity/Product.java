package ra.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proId;
    @Column(name = "product_name",length = 100,nullable = false)
    private String proName;
    @Column(length = 100, nullable = false)
    private String producer;
    @Column(name = "year_making")
    private Integer yearMaking;
    @Column(name = "expire_date")
    private LocalDate expireDate;

    @ManyToOne // Mặc định là FETYPE.EAGER
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;
}
