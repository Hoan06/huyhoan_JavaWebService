package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}