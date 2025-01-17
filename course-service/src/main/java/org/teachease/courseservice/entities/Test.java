package org.teachease.courseservice.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private boolean isAiGenerated;
    @Column(nullable = false)
    private String courseId;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
    private boolean aiEvaluation;
    private Long totalTime;
    private int points;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAiGenerated() {
        return isAiGenerated;
    }

    public void setAiGenerated(boolean aiGenerated) {
        isAiGenerated = aiGenerated;
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

    public boolean isAiEvaluation() {
        return aiEvaluation;
    }

    public void setAiEvaluation(boolean aiEvaluation) {
        this.aiEvaluation = aiEvaluation;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
