package com.myspringapp.employeedemo.dto;

import com.myspringapp.employeedemo.entity.Task;

import java.util.Date;
import java.util.List;

public record ProjectDTO(
        Integer id,
        String name,
        EmployeeDTO manager,
        String description,
        String type,
        Date createdAt,
        boolean completed,
        List<EmployeeDTO> team,
        List<Task> tasks


) {
}
