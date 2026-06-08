package ra.service;

import ra.model.dto.request.EmployeeDTO;
import ra.model.dto.request.LoginDTO;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.Employee;

import java.util.List;


public interface EmployeeService {
    Employee register(EmployeeDTO employeeDTO);
    JWTResponse login(LoginDTO employeeLogin);
    List<Employee> getEmployees();
}
