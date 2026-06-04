package ra.service.impl;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.exception.UniqueEmailException;
import ra.model.dto.request.UserDTO;
import ra.model.dto.request.UserLogin;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.User;
import ra.repository.UserRepository;
import ra.security.jwt.JWTProvider;
import ra.security.principle.CustomUserDetail;
import ra.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Override
    public User registerUser(UserDTO userDTO) {
        User userCheck = userRepository.findByEmail(userDTO.getEmail());
        if (userCheck != null) {
            throw new UniqueEmailException("Email đã tồn tại");
        }
        User user = User.builder()
                .phone(userDTO.getPhone())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .roles(userDTO.getRoles())
                .build();
        return userRepository.save(user);
    }

    @Override
    public JWTResponse login(UserLogin userLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
            CustomUserDetail  customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            String token = jwtProvider.generateToken(customUserDetail.getUsername());

            return JWTResponse.builder()
                    .phone(customUserDetail.getPhone())
                    .email(customUserDetail.getEmail())
                    .authorities(customUserDetail.getAuthorities())
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
