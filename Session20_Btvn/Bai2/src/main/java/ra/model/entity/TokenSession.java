package ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token_sessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class TokenSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token_value", length = 500)
    private String refreshTokenValue;

    private boolean isRevoked;
    private boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}