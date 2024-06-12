package com.myspringapp.employeedemo.dao;

import com.myspringapp.employeedemo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
