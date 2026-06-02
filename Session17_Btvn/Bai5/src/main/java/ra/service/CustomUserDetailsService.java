package ra.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encodedPassword = "$2a$10$e3qh7UzmA7g1izX83jkVKuy3CBOXHzD6Z8SSYIWNdEK8WIY5lgVMu";

        if ("player1".equals(username)) {
            return new User("player1", encodedPassword, Collections.singletonList(new SimpleGrantedAuthority("ROLE_PLAYER")));
        }
        if ("mod1".equals(username)) {
            return new User("mod1", encodedPassword, Collections.singletonList(new SimpleGrantedAuthority("ROLE_GAME_MODERATOR")));
        }
        if ("admin1".equals(username)) {
            return new User("admin1", encodedPassword, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        throw new UsernameNotFoundException("User not found");
    }
}
