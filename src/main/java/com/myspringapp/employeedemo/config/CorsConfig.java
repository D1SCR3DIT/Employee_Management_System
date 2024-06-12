package com.myspringapp.employeedemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Specify the mapping path for your APIs
                .allowedOrigins("http://localhost:5173") // Allow requests from your React frontend domain
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                .allowedHeaders("*"); // Allow all headers
    }
}
