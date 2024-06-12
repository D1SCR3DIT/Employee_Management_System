package com.myspringapp.employeedemo.service.project;

import com.myspringapp.employeedemo.dao.EmployeeRepository;
import com.myspringapp.employeedemo.dao.ProjectRepository;
import com.myspringapp.employeedemo.dao.TaskRepository;
import com.myspringapp.employeedemo.entity.Employee;
import com.myspringapp.employeedemo.entity.Project;
import com.myspringapp.employeedemo.entity.Role;
import com.myspringapp.employeedemo.entity.Task;
import com.myspringapp.employeedemo.dto.EmployeeDTO;
import com.myspringapp.employeedemo.dto.ProjectRequest;
import com.myspringapp.employeedemo.dto.ProjectDTO;
import com.myspringapp.employeedemo.errorhandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    private EmployeeDTO employeeResponse(Employee employee) {

        if(employee == null) return null;

        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername(),
                employee.getEmail(),
                employee.getRole(),
                employee.getProjectId()
        );
    }

    private ProjectDTO projectResponse(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                employeeResponse(project.getManager()),
                project.getDescription(),
                project.getType(),
                project.getCreatedAt(),
                project.isCompleted(),
                project.getTeam() != null ? project.getTeam().stream()
                        .map(this::employeeResponse)
                        .toList() : null,
                project.getTasks()
        );
    }

    @Override
    public ProjectDTO getProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Project with ID: " + id + " does not exist"));

        return projectResponse(project);
    }

    @Override
    public List<ProjectDTO> findAllProjects() {
        List<Project> projects =  projectRepository.findAll();

        return projects.stream()
                .map(this::projectResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProjectDTO createProject(ProjectRequest project) {

        Project newProject = Project.builder()
                .name(project.name())
                .description(project.description())
                .type(project.type())
                .createdAt(new Date(System.currentTimeMillis()))
                .build();

        return projectResponse(projectRepository.save(newProject));
    }

    @Override
    @Transactional
    public ProjectDTO updateProject(int id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Project:" + id + " you are trying to update does not exist ;)"));

        if(request.name() != null) project.setName(request.name());
        if(request.description() != null) project.setDescription(request.description());
        if(request.type() != null) project.setType(request.type());

        return projectResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public ProjectDTO deleteProject(int id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Project you are trying to delete does not exist"));

        Hibernate.initialize(project.getTeam());
        Hibernate.initialize(project.getTasks());

        Employee manager = null;
        if(project.getManager() != null) {
            manager = project.getManager();
            manager.setRole(Role.ROLE_EMPLOYEE);
            employeeRepository.save(manager);
        }

        if(project.getTasks() != null) {
            for(Task task : project.getTasks()) {
                task.setProject(null);
                taskRepository.deleteById(task.getId());

            }
        }

        for (Employee employee : project.getTeam()) {
            employee.setProject(null);
        }

        projectRepository.deleteById(id);

        return projectResponse(project);

    }

    @Override
    @Transactional
    public ProjectDTO addProjectTeam(int id, List<Integer> employeeIds) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Project with ID: " + id + " does not exist"));

        if(project == null) {
            return null;
        }

        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        if(employees.isEmpty()) {
            return null;
        }

        project.addEmployeesToProject(employees);
        return projectResponse(projectRepository.save(project));


    }

    @Override
    @Transactional
    public ProjectDTO setProjectManager(int id, int employeeId) {
        Employee manager = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Employee to be assigned as manager with ID: " + employeeId + "does not exist"));
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Project with ID: " + id + " does not exist"));

        Employee preManager = null;

        if(project.getManager() != null) {
            preManager = project.getManager();
            preManager.setProject(null);
            preManager.setRole(Role.ROLE_EMPLOYEE);
            employeeRepository.save(preManager);
        }


        manager.setRole(Role.ROLE_MANAGER);
        manager.setProject(project);
        project.setManager(manager);

        employeeRepository.save(manager);
        return projectResponse(projectRepository.save(project));
    }


}
