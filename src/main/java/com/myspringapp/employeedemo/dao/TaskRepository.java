package com.myspringapp.employeedemo.dao;

import com.myspringapp.employeedemo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
