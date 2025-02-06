package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.teachease.courseservice.dtos.CourseDTO;
import org.teachease.courseservice.dtos.ModuleDTO;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "course")
@Data
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private String teacherId;



    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Module> modules;

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
