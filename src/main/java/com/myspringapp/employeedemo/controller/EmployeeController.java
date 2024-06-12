package com.myspringapp.employeedemo.controller;

import com.myspringapp.employeedemo.entity.Employee;
import com.myspringapp.employeedemo.dto.EmployeeDTO;
import com.myspringapp.employeedemo.dto.RegisterRequest;
import com.myspringapp.employeedemo.service.email.EmailService;
import com.myspringapp.employeedemo.service.employee.EmployeeService;
import com.myspringapp.employeedemo.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // Fetch all the employees
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> findAllEmployees() {
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

    // Fetch single employee using employee id
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    // Create an employee
    @PostMapping("/admin/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(employeeService.createEmployee(request));
    }


    private EmployeeDTO employeeResponse(Employee employee) {

        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername(),
                employee.getEmail(),
                employee.getRole(),
                employee.getProjectId()
        );
    }

    // Create multiple employees
    @PostMapping("/admin/employees/all")
    public ResponseEntity<List<Employee>> addEmployeeList(@RequestBody List<RegisterRequest> employees) {
        return ResponseEntity.ok(employeeService.addEmployeeList(employees));
    }


    // Update an employee
    @PutMapping("/employees")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employee));
    }

    // Delete an employee
    @DeleteMapping("/admin/employees/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
