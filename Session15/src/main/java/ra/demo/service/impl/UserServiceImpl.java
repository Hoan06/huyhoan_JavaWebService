package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.demo.model.dto.request.UserDTO;
import ra.demo.model.entity.Users;
import ra.demo.repository.UserRepository;
import ra.demo.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
}
