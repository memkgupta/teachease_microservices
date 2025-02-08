package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "module_content")
@Data
public class ModuleContent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;
    private String title;
    private String description;
    private String resourceUrl;
    private boolean isAiGenerated;
    private Long fileSize;
    private String fileType;
    private Timestamp createdAt;
    private boolean hidden = false;
}
