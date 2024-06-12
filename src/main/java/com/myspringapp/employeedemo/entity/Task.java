package com.myspringapp.employeedemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_At")
    private Date createdAt;

    @Column(name = "completed", columnDefinition = "BOOLEAN")
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    public Task(String taskName, String description) {
        this.name = taskName;
        this.description = description;
        this.createdAt = new Date(System.currentTimeMillis());
        this.completed = false;
    }

    @JsonProperty("projectId")
    public Integer getProjectId() {
        return (project != null) ? project.getId() : null;
    }


}
