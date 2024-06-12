package com.myspringapp.employeedemo.controller;

import com.myspringapp.employeedemo.dto.UserDTO;
import com.myspringapp.employeedemo.dto.LoginRequest;
import com.myspringapp.employeedemo.dto.RegisterRequest;
import com.myspringapp.employeedemo.service.authentication.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
