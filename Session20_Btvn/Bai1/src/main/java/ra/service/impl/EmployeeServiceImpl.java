package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.model.dto.request.EmployeeDTO;
import ra.model.dto.request.LoginDTO;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.Employee;
import ra.repository.EmployeeRepository;
import ra.security.jwt.JWTProvider;
import ra.security.principal.CustomUserDetails;
import ra.service.EmployeeService;
import ra.service.TokenService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final TokenService tokenService;

    @Override
    public Employee register(EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder()
                .username(employeeDTO.getUsername())
                .password(passwordEncoder.encode(employeeDTO.getPassword()))
                .active(true)
                .roles(employeeDTO.getRoles())
                .build();
        return employeeRepository.save(employee);
    }

    @Override
    public JWTResponse login(LoginDTO employeeLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeLogin.getUsername(), employeeLogin.getPassword()));
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtProvider.generateToken(userDetails.getUsername());
            String refreshToken = tokenService.createRefreshToken(userDetails.getUsername()).getTokenValue();

            return JWTResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
}
