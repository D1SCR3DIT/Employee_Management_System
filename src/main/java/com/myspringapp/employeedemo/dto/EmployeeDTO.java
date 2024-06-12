package com.myspringapp.employeedemo.dto;

import com.myspringapp.employeedemo.entity.Role;

public record EmployeeDTO(
        Integer id,
        String firstName,
        String lastName,
        String username,
        String email,
        Role role,
        Integer projectId
) {
}
