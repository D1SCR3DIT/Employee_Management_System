package com.myspringapp.employeedemo.entity;


public enum Role {
    ROLE_EMPLOYEE(" ROLE_EMPLOYEE"),

    ROLE_MANAGER(" ROLE_MANAGER"),

    ROLE_ADMIN("ROLE_ADMIN");

    public final String role;

    private Role(String role) {
        this.role = role;
    }
}
