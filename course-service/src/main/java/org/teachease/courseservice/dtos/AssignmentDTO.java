package org.teachease.courseservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private Integer points;

    private String courseId;
    private ModuleDTO module;
}
