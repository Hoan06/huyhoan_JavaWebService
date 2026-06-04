package ra.service.impl;

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

        if ("admin".equals(username)) {
            return User.withUsername("admin").password(encoder.encode("admin123")).roles("ADMIN").build();
        } else if ("staff".equals(username)) {
            return User.withUsername("staff").password(encoder.encode("staff123")).roles("STAFF").build();
        } else if ("customer".equals(username)) {
            return User.withUsername("customer").password(encoder.encode("customer123")).roles("CUSTOMER").build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}