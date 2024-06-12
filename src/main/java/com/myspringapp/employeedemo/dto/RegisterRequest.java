package com.myspringapp.employeedemo.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String username,
        String email,
        String password
) {
}
