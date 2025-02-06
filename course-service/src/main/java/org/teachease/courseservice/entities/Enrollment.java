package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "course_enrollment",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"course","student"})
})
@Data
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;
    private String studentId;
    private boolean active;
    private Timestamp lastActivity;
    private Timestamp createdAt;
    private int points;


}
