package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.NotesDTO;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String courseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;
    private String title;
    @Column(nullable = false)
    private String resourceURL;
    private String description;
    private String fileType;
    private Long fileSize;
    private boolean isAiGenerated;

    public NotesDTO getPartialDTO(){
        return NotesDTO.builder()
                .id(this.id)
                .courseId(this.courseId)
                .module(ModuleDTO.builder()
                        .id(module.getId())
                        .title(module.getTitle())
                        .build())
                .title(this.title)
                .fileType(this.fileType)
                .build();
    }

}
