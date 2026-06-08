package ra.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.model.entity.Employee;
import ra.model.entity.Role;
import ra.repository.EmployeeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("Không tìm thấy employee có username : "  + username));

        return CustomUserDetails.builder()
                .username(employee.getUsername())
                .password(employee.getPassword())
                .active(employee.isActive())
                .authorities(mapToGrandAuthorities(employee.getRoles()))
                .build();
    }

    private List<? extends GrantedAuthority> mapToGrandAuthorities(List<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
    }
}
