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
    public List<Employee> getAllEmployees() {
        return Arrays.asList(
                new Employee(1L, "Nguyen Huy Hoan", 1500.0),
                new Employee(2L, "Tran Van A", 1200.0),
                new Employee(3L, "Le Thi B", 1350.0)
        );
    }
}