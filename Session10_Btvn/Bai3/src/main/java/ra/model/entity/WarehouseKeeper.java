package ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouse_keepers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseKeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String staffCode;
}