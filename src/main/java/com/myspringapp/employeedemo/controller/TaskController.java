package com.myspringapp.employeedemo.controller;

import com.myspringapp.employeedemo.entity.Task;
import com.myspringapp.employeedemo.dto.TaskRequest;
import com.myspringapp.employeedemo.service.project.ProjectService;
import com.myspringapp.employeedemo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    // Fetch all the tasks
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> findAllTasks() {
        return ResponseEntity.ok(taskService.findAllTasks());
    }

    // Fetch a specific task using task id
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Task> findTaskById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.findTaskById(id));
    }

    // Create one task for a specific project using project id
    @PostMapping("/manager/projects/{projectId}/task")
    public ResponseEntity<Task> createTask(@PathVariable Integer projectId, @RequestBody TaskRequest task) {
        return ResponseEntity.ok(taskService.createTask(projectId, task));
    }

    // Create multiple tasks for a specific project using project id
    @PostMapping("/manager/projects/{projectId}/tasks")
    public ResponseEntity<List<Task>> createMultipleTask(@PathVariable Integer projectId, @RequestBody List<Task> tasks) {
        return ResponseEntity.ok(taskService.createMultipleTask(projectId, tasks));
    }

    // Delete a specific task using task id
    @DeleteMapping("/manager/tasks/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    // Set a specific task as complete
    @PutMapping("/manager/tasks/{id}")
    public ResponseEntity<Task> completeTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.completeTask(id));
    }

}
