package com.myspringapp.employeedemo.service.employee;

import com.myspringapp.employeedemo.entity.Employee;
import com.myspringapp.employeedemo.dto.EmployeeDTO;
import com.myspringapp.employeedemo.dto.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    public EmployeeDTO findEmployeeById(Integer employeeId);

    public List<EmployeeDTO> findAllEmployees();

    public EmployeeDTO createEmployee(RegisterRequest request);

    public List<Employee> addEmployeeList(List<RegisterRequest> employees);

    public EmployeeDTO updateEmployee(Employee employee);

    public EmployeeDTO deleteEmployee(int employeeId);

}
