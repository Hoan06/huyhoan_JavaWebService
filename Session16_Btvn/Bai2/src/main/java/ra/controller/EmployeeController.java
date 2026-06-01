package ra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.model.entity.Employee;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @GetMapping
    public List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(1L, "Alice Watson", 2500.0),
                new Employee(2L, "Bob Vance", 2800.0),
                new Employee(3L, "Nguyen Huy Hoan", 3000.0)
        );
    }
}