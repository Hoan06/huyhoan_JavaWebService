package ra.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if ("khachhang".equals(username)) {
            return User.withUsername("khachhang")
                    .password(encoder.encode("password123"))
                    .roles("CUSTOMER")
                    .build();
        } else if ("nhanvien".equals(username)) {
            return User.withUsername("nhanvien")
                    .password(encoder.encode("password123"))
                    .roles("STAFF")
                    .build();
        }

        throw new UsernameNotFoundException("Không tìm thấy tài khoản test: " + username);
    }
}