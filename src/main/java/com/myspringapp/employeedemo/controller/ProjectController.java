package com.myspringapp.employeedemo.controller;

import com.myspringapp.employeedemo.dto.ProjectRequest;
import com.myspringapp.employeedemo.dto.ProjectDTO;
import com.myspringapp.employeedemo.service.employee.EmployeeService;
import com.myspringapp.employeedemo.service.project.ProjectService;
import com.myspringapp.employeedemo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final TaskService taskService;

    // Fetch all the projects
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> findAllProjects() {
        return ResponseEntity.ok(projectService.findAllProjects());
    }

    // Fetch single project using project id
    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    // Create a new project
    @PostMapping("/admin/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectRequest project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    // Update existing project
    @PutMapping("/admin/projects/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable int id, @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    // Delete an existing project using project id
    @DeleteMapping("/admin/projects/{id}")
    public ResponseEntity<ProjectDTO> deleteProject(@PathVariable int id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }

    // Assign project to employees
    @PostMapping("/admin/projects/{id}/addEmployees")
    public ResponseEntity<ProjectDTO> addProjectTeam(@PathVariable int id, @RequestBody List<Integer> employeeIds) {
        return ResponseEntity.ok(projectService.addProjectTeam(id, employeeIds));
    }

    // Assign project manager to a project
    @PostMapping("/admin/projects/{id}/addManager")
    public ResponseEntity<ProjectDTO> setProjectManager(@PathVariable int id, @RequestBody int employeeId) {
        return ResponseEntity.ok(projectService.setProjectManager(id, employeeId));

    }
}
