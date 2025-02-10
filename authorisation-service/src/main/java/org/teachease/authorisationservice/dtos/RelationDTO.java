package org.teachease.authorisationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
