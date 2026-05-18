package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long cateId;
    @Column(name = "category_name" , length = 100 , nullable = false , unique = true)
    private String cateName;
    private Boolean status;

    @OneToMany(mappedBy = "category") // Mặc định là fetch lazy
    @JsonIgnore
    private List<Product> products;
}
