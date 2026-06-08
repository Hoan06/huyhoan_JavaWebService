package ra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.jwt.JwtProvider;
import ra.model.dto.request.LoginRequest;
import ra.model.dto.request.RefreshRequest;
import ra.model.dto.response.JwtResponse;
import ra.model.entity.Student;
import ra.model.entity.StudentToken;
import ra.repository.StudentRepository;
import ra.repository.StudentTokenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final StudentTokenRepository studentTokenRepository;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Tài khoản hoặc mật khẩu không đúng"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không đúng");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(student.getEmail());
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        String refreshToken = jwtProvider.generateRefreshToken(userDetails);

        StudentToken token = StudentToken.builder()
                .tokenString(refreshToken)
                .isRevoked(false)
                .isExpired(false)
                .studentId(student.getId())
                .build();
        studentTokenRepository.save(token);

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refresh(RefreshRequest request) {
        StudentToken token = studentTokenRepository.findByTokenString(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh Token không hợp lệ"));

        if (token.isRevoked() || token.isExpired()) {
            throw new RuntimeException("Refresh Token đã bị hủy hoặc hết hạn");
        }

        String email = jwtProvider.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!jwtProvider.isTokenValid(request.getRefreshToken(), userDetails)) {
            throw new RuntimeException("Token không hợp lệ");
        }

        String newAccessToken = jwtProvider.generateAccessToken(userDetails);
        return new JwtResponse(newAccessToken, request.getRefreshToken());
    }

    public void logout(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        List<StudentToken> tokens = studentTokenRepository.findByStudentIdAndIsRevokedFalse(student.getId());

        tokens.stream().forEach(t -> {
            t.setRevoked(true);
            studentTokenRepository.save(t);
        });

        SecurityContextHolder.clearContext();
    }
}