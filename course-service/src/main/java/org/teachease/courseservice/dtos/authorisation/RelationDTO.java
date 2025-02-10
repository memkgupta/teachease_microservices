package org.teachease.courseservice.dtos.authorisation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class RelationDTO {
    private String id;
    private Timestamp created;
    private String name;
    private String userId;
    private String resourceId;
    private String resourceName;
    private String description;
    private ResourceDTO resource;
}
