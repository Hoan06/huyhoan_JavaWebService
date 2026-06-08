package ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    private String username;
    @Column(name = "token_value", nullable = false , columnDefinition = "TEXT")
    private String tokenValue;
    @Column(name = "token_type" ,  nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean revoked;
    private boolean expired;
    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
