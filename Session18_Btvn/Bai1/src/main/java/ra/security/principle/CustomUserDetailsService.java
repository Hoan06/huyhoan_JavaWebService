package ra.security.principle;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.model.entity.Role;
import ra.model.entity.User;
import ra.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tồn tại người dùng có email : " + username);
        }
        return CustomUserDetail.builder()
                .phone(user.getPhone())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(mapToGrantedAuthorities(user.getRoles()))
                .build();
    }

    private List<? extends GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRoleName())).toList();
    }
}
