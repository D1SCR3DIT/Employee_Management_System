package com.myspringapp.employeedemo.config;

import com.myspringapp.employeedemo.filter.JwtAuthenticationFilter;
import com.myspringapp.employeedemo.service.authentication.EmployeeDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final EmployeeDetailsServiceImpl employeeDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(EmployeeDetailsServiceImpl employeeDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.employeeDetailsService = employeeDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/api/login/**", "/api/register/**").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
                                .anyRequest().authenticated()
                ).userDetailsService(employeeDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
