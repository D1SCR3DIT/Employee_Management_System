package com.myspringapp.employeedemo.service.employee;

import com.myspringapp.employeedemo.dao.EmployeeRepository;
import com.myspringapp.employeedemo.dao.ProjectRepository;
import com.myspringapp.employeedemo.entity.Employee;
import com.myspringapp.employeedemo.entity.Project;
import com.myspringapp.employeedemo.entity.Role;
import com.myspringapp.employeedemo.dto.EmployeeDTO;
import com.myspringapp.employeedemo.dto.RegisterRequest;
import com.myspringapp.employeedemo.errorhandler.CustomException;
import com.myspringapp.employeedemo.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public EmployeeDTO findEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Employee with ID: " + id + " does not exist!"));

        return employeeResponse(employee);
    }
    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::employeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(RegisterRequest request) {
        String rawPassword = request.password();

        Employee newEmployee = Employee.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.ROLE_EMPLOYEE)
                .build();

        employeeRepository.save(newEmployee);

        String emailMessage = String.format("Hello %s %s,\n\nUse the credentials below to login to the system.\nUsername: %s\nPassword: %s\n\nRegards,\nYour Company",
                newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getUsername(), rawPassword);
        emailService.sendEmail(newEmployee.getEmail(), "Company Account Credentials", emailMessage);

        return employeeResponse(newEmployee);
    }

    @Override
    @Transactional
    public List<Employee> addEmployeeList(List<RegisterRequest> employees) {
        List<Employee> newEmployees = employees.stream()
                .map(emp ->
                        Employee.builder()
                                .firstName(emp.firstName())
                                .lastName(emp.lastName())
                                .username(emp.username())
                                .email(emp.email())
                                .password(passwordEncoder.encode(emp.password()))
                                .role(Role.ROLE_EMPLOYEE)
                                .build())
                .toList();

        employeeRepository.saveAll(newEmployees);

        return newEmployees;
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Employee employee) {
        Employee existing = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Employee with ID: " + employee.getId() + " does not exist!"));

        if(employee.getPassword() != null) existing.setPassword(passwordEncoder.encode(employee.getPassword()));

        return employeeResponse(employeeRepository.save(existing));
    }

    @Override
    @Transactional
    public EmployeeDTO deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Employee you are trying to delete does not exist :("));
        Project project = null;
        if(employee.getProjectId() != null) {
            project = projectRepository.findById(employee.getProjectId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "The project assigned to the Employee does not exist :("));
            employee.setProject(null);
            if(employee.getRole() == Role.ROLE_MANAGER) {
                project.setManager(null);
            }
            projectRepository.save(project);
        }

        employeeRepository.deleteById(id);

        return employeeResponse(employee);
    }

}
