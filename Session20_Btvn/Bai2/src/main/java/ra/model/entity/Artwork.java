package ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artworks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Account owner;
}