package com.myspringapp.employeedemo.service.task;

import com.myspringapp.employeedemo.entity.Task;
import com.myspringapp.employeedemo.dto.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    Task findTaskById(Integer id);

    public List<Task> findAllTasks();

    public Task createTask(Integer projectId, TaskRequest task);

    public List<Task> createMultipleTask(Integer projectId, List<Task> tasks);

    public Task deleteTask(Integer id);

    public Task completeTask(Integer id);
}
