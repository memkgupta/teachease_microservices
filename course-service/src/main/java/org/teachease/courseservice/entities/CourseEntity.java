package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import org.teachease.courseservice.dtos.CourseDTO;
import org.teachease.courseservice.dtos.ModuleDTO;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private String teacherId;

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public ModuleList getModules() {
        return modules;
    }

    public void setModules(ModuleList modules) {
        this.modules = modules;
    }

    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    @OneToOne(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private ModuleList modules;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public CourseDTO courseDTO(){
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(id);
        courseDTO.setTitle(title);
        courseDTO.setDescription(description);
        courseDTO.setTeacherId(teacherId);
        courseDTO.setStartDate(startDate);
        courseDTO.setEndDate(endDate);
        courseDTO.setCreatedAt(createdAt);

        return courseDTO;
    }
}
