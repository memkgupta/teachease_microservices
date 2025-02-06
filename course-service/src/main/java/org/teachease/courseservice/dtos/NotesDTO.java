package org.teachease.courseservice.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teachease.courseservice.entities.Module;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesDTO {
    private String id;
    private String courseId;
    private ModuleDTO module;
    private String title;
    private String resourceURL;
    private String description;
    private String fileType;
    private Long fileSize;
    private boolean isAiGenerated;
}
