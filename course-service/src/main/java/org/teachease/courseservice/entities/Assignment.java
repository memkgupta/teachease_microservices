package org.teachease.courseservice.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssignmentResource() {
        return assignmentResource;
    }

    public void setAssignmentResource(String assignmentResource) {
        this.assignmentResource = assignmentResource;
    }

    public String getSolutionResource() {
        return solutionResource;
    }

    public void setSolutionResource(String solutionResource) {
        this.solutionResource = solutionResource;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public boolean isAiGenerated() {
        return isAiGenerated;
    }

    public void setAiGenerated(boolean aiGenerated) {
        isAiGenerated = aiGenerated;
    }

    private boolean isAiGenerated;

}
