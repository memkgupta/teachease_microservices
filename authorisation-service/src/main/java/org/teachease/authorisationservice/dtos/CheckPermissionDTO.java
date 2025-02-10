package org.teachease.authorisationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckPermissionDTO {
    private String resourceId;
    private String resourceName;
    private String action;
    private String userId;
    private String extraInfo;

}
