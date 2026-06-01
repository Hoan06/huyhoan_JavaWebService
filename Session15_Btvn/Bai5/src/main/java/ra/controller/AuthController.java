package ra.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.model.dto.request.AuthRequest;
import ra.model.dto.response.AuthResponse;
import ra.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new AuthResponse("Username đã tồn tại"));
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.saveUser(request.getUsername(), encodedPassword);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse("Đăng kí thành công"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        if (!userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("username or password không đúng"));
        }

        String storedEncodedPassword = userRepository.findPasswordByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), storedEncodedPassword)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("username or password không đúng"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse("Đăng nhập thành công"));
    }
}