package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teachease.courseservice.dtos.ContentDTO;
import org.teachease.courseservice.dtos.ModuleDTO;

import java.sql.Timestamp;

@Entity
@Table(name = "content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;
    private String title;
    private String description;
    private String resourceUrl;
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum resourceType;
    private boolean isAiGenerated;
    private Long fileSize;
    private String thumbnail;
    private String fileType;
    private Timestamp createdAt;
    private Timestamp scheduledAt;
    private boolean hidden = false;
    private String courseId;

    public ContentDTO partialDTO(){
        ContentDTO contentDTO = ContentDTO.builder()
                .id(this.id)
                .description(description)
                .createdAt(createdAt)
                .scheduledAt(scheduledAt)
                .hidden(hidden)
                .fileType(fileType)
                .fileSize(fileSize)
                .courseId(courseId)
                .resourceUrl(resourceUrl)
                .thumbnail(thumbnail)
                .module(ModuleDTO.builder()
                        .id(module.getId())
                        .title(module.getTitle())
                        .build())
                .title(title)
                .build();
        return contentDTO;
    }

}

