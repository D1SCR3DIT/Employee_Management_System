package com.myspringapp.employeedemo.dto;

import com.myspringapp.employeedemo.entity.Role;

public record UserDTO(
        Integer id,
        String username,
        Role role,
        String token,
        Integer projectId,
        String email
) {
}
