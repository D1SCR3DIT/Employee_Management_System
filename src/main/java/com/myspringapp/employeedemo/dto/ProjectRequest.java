package com.myspringapp.employeedemo.dto;

public record ProjectRequest(
        String name,
        String description,
        String type
) {
}
