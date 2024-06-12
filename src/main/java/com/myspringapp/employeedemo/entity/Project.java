package com.myspringapp.employeedemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "project_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private Employee manager;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "project_type")
    private String type;

    @Column(name = "created_At")
    private Date createdAt;

    @Column(name = "completed", columnDefinition = "BOOLEAN")
    private boolean completed;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Employee> team;


    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Task> tasks;

    public Project(String name, Employee manager, List<Employee> team, List<Task> tasks) {
        this.name = name;
        this.manager = manager;
        this.team = team != null ? team : new ArrayList<>();
        this.tasks = tasks != null ? tasks : new ArrayList<>();
        this.completed = false;
        this.createdAt = new Date(System.currentTimeMillis());
    }

    // add convenience method to add the project manager
    public void addProjectManager(Employee employee) {
        setManager(employee);
    }

    // add convenience method to add an employee to the team
    public void addEmployeesToProject(List<Employee> employees) {
        if (team == null) {
            team = new ArrayList<>();
        }
        team.addAll(employees);
        for (Employee employee : employees) {
            employee.setProject(this);
        }
    }

    // method to add a single task to the project
    public void addTask(Task newTask) {
        if(tasks == null) {
            tasks = new ArrayList<>();
        }

        tasks.add(newTask);
        newTask.setProject(this);
    }

    // method to add multiple tasks to the project
    public void addTasks(List<Task> newTasks) {
        if(tasks == null) {
            tasks = new ArrayList<>();
        }

        tasks.addAll(newTasks);
        for(Task task : newTasks) {
            task.setCreatedAt(new Date(System.currentTimeMillis()));
            task.setProject(this);
        }

    }



}
