package ra.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account_balances")
public class AccountBalance {
    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "cash_available", nullable = false)
    private Double cashAvailable;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Double getCashAvailable() { return cashAvailable; }
    public void setCashAvailable(Double cashAvailable) { this.cashAvailable = cashAvailable; }
}