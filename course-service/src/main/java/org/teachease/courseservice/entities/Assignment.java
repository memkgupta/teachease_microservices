package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.teachease.courseservice.dtos.AssignmentDTO;
import org.teachease.courseservice.dtos.ModuleDTO;


import java.sql.Timestamp;

@Entity
@Table
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private String title;
    private String assignmentResource;
    private String solutionResource;
    private Timestamp createdAt;
    private Timestamp dueDate;
    private int points;
    @Column(nullable = false)
    private String courseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;



    private boolean isAiGenerated;
public AssignmentDTO toPartialDTO() {
    return AssignmentDTO.builder()
            .module(ModuleDTO.builder()
                    .title(module.getTitle())
                    .id(module.getId())
                    .build())
            .courseId(courseId)
            .title(title)
            .points(points)
            .build();
}

    public AssignmentDTO getPartialDTO() {
    return AssignmentDTO.builder()
            .id(id)
            .module(module.getPartialDTO())
            .title(title)
            .points(points)
            .dueDate(dueDate)
            .createdAt(createdAt)
            .assignmentResource(assignmentResource)
            .solutionResource(solutionResource)
            .courseId(courseId)
            .build();
    }
}
