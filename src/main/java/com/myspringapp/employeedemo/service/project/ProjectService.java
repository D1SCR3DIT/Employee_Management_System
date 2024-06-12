package com.myspringapp.employeedemo.service.project;

import com.myspringapp.employeedemo.dto.ProjectRequest;
import com.myspringapp.employeedemo.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProjectService {

    ProjectDTO getProject(Integer id);

    List<ProjectDTO> findAllProjects();

    ProjectDTO createProject(ProjectRequest project);

    public ProjectDTO updateProject(int id, ProjectRequest request);

    ProjectDTO deleteProject(int id);

    ProjectDTO addProjectTeam(int id, List<Integer> employeeIds);

    ProjectDTO setProjectManager(int id, int employeeId);
}
