package com.myspringapp.employeedemo.dao;

import com.myspringapp.employeedemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
        Optional<Employee> findByUsername(String username);
}
