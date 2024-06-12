package com.myspringapp.employeedemo.dto;

public record LoginRequest(
        String username,
        String password
) {
}
