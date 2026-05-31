package ra.edu.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medications")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100,nullable = false,unique = true)
    private String name;
    @Column(length = 100, nullable = false)
    private String manufacturer;
    private Double price;
    private StatusType status;
    @Column(name = "is_delete")
    private Boolean isDelete;
}
