package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.demo.model.dto.request.UserDTO;
import ra.demo.model.dto.request.UserLogin;
import ra.demo.model.dto.response.JWTResponse;
import ra.demo.model.entity.Users;
import ra.demo.repository.UserRepository;
import ra.demo.security.jwt.JWTProvider;
import ra.demo.security.principal.CustomUserDetails;
import ra.demo.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;


    @Override
    public Users registerUser(UserDTO userDTO) {
        Users users = Users.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .roles(userDTO.getRoles())
                .enabled(true)
                .build();
        return userRepository.save(users);
    }

    @Override
    public JWTResponse login(UserLogin userLogin) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtProvider.generateToken(userDetails.getUsername());

            return JWTResponse.builder()
                    .username(userDetails.getUsername())
                    .fullName(userDetails.getFullName())
                    .email(userDetails.getEmail())
                    .enabled(userDetails.getEnabled())
                    .authorities(userDetails.getAuthorities())
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            log.info("Sai username hoặc password ",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


}
