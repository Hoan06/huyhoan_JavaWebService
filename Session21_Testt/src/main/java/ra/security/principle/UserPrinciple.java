package ra.security.principle;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.model.entity.User;
import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple build(User user) {
        return new UserPrinciple(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}