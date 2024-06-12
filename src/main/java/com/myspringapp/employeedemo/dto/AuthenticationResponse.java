package com.myspringapp.employeedemo.dto;

import com.myspringapp.employeedemo.entity.Employee;
import com.myspringapp.employeedemo.dto.UserDTO;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private Employee employee;
    private UserDTO userDTO;



    public AuthenticationResponse(String token, Employee employee) {
        this.token = token;
        this.employee = employee;
        this.userDTO = new UserDTO(employee.getId(), employee.getUsername(), employee.getRole(),this.token, employee.getProjectId(), employee.getEmail());
    }

    public Employee getUser() {
        return employee;
    }

}
