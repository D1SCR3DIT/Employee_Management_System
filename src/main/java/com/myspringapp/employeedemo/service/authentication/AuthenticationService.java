package com.myspringapp.employeedemo.service.authentication;

import com.myspringapp.employeedemo.dao.EmployeeRepository;
import com.myspringapp.employeedemo.dto.AuthenticationResponse;
import com.myspringapp.employeedemo.entity.Employee;
import com.myspringapp.employeedemo.entity.Role;
import com.myspringapp.employeedemo.dto.UserDTO;
import com.myspringapp.employeedemo.dto.LoginRequest;
import com.myspringapp.employeedemo.dto.RegisterRequest;
import com.myspringapp.employeedemo.errorhandler.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public UserDTO register(RegisterRequest request) {
        Employee employee = Employee.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_ADMIN)
                .build();

        employeeRepository.save(employee);

        String token = jwtService.generateToken(employee);

        AuthenticationResponse authRes = new AuthenticationResponse(token, employee);

        return authRes.getUserDTO();
    }

    public UserDTO authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        Employee employee = employeeRepository.findByUsername(request.username())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Either the Username or Password is incorrect!!"));
        String token = jwtService.generateToken(employee);

        AuthenticationResponse authRes = new AuthenticationResponse(token, employee);

        return authRes.getUserDTO();
    }
}
