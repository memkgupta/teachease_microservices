package org.teachease.courseservice.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentDTO {
    private String id;
    private String title;
    private String description;
    private String resourceUrl;
    private String thumbnail;
    private String courseId;
    private String resourceType;
//    private String fileType;
    private boolean isAiGenerated;
    private String fileType;
    private Long fileSize;
    private Timestamp createdAt;
    private Timestamp scheduledAt;
    private ModuleDTO module;
    private boolean hidden;
}
