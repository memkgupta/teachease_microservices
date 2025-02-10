package org.teachease.courseservice.dtos.authorisation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class ResourceDTO {
    private String resourceId;
    private String resourceName;
    private String resourceType;
    private String owner;
    private Timestamp created;
}
