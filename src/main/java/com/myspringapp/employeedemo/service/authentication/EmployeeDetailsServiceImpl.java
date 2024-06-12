package com.myspringapp.employeedemo.service.authentication;

import com.myspringapp.employeedemo.dao.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
