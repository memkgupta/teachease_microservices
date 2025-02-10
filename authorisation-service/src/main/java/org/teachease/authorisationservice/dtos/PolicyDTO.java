package org.teachease.authorisationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyDTO {
    private String id;
    private String name;
    private String action;
    private String description;
    private String conditions;
    private String resourceName;
}
