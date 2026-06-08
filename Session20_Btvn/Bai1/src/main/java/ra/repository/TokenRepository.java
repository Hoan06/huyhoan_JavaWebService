package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Token;
import ra.model.entity.TokenType;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenValue(String token);
    List<Token> findAllByEmployee_IdAndRevokedFalseAndExpiredFalse(Long employeeId);
    Optional<Token> findByTokenValueAndTokenTypeAndRevokedFalseAndExpiredFalse(String tokenValue , TokenType tokenType);
    Token findByTokenValueAndTokenType(String tokenValue , TokenType tokenType);
}
