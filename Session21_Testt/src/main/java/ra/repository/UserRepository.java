package ra.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}