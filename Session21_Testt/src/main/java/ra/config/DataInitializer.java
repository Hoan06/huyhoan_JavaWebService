package ra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ra.model.entity.User;
import ra.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("nguyenhuyhoan").isEmpty()) {
            User testUser = User.builder()
                    .username("nguyenhuyhoan")
                    .password(passwordEncoder.encode("hoan2026"))
                    .fullName("Nguyễn Huy Hoàn")
                    .role("ROLE_USER")
                    .build();
            userRepository.save(testUser);
        }
    }
}