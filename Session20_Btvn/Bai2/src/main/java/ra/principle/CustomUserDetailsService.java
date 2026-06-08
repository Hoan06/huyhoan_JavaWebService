package ra.principle;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encodedPassword = new BCryptPasswordEncoder().encode("password123");

        if ("admin_user".equals(username)) {
            return new User(
                    "admin_user",
                    encodedPassword,
                    true, true, true, true,
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        if ("artist_user".equals(username)) {
            return new User(
                    "artist_user",
                    encodedPassword,
                    true, true, true, true,
                    List.of(new SimpleGrantedAuthority("ROLE_ARTIST"))
            );
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}