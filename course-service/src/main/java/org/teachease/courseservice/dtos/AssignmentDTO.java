package org.teachease.courseservice.dtos;


import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teachease.courseservice.entities.Module;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {
    private String id;
    private String description;
    private String title;
    private String assignmentResource;
    private String solutionResource;
    private Timestamp createdAt;
    private Timestamp dueDate;
    private int points;

    private String courseId;
    private ModuleDTO module;
}
