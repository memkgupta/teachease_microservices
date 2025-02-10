package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "lecture")
@Data
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private String thumbnail;
    private String url;
    private boolean isLive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp scheduledAt;
    private boolean isHidden;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;
    private String courseId;
}
