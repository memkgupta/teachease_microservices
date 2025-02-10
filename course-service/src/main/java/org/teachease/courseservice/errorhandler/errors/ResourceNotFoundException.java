package org.teachease.courseservice.errorhandler.errors;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ResourceNotFoundException extends RuntimeException {
    private String resourceId;
    private String errorMessage;
    private String resourceName;
    private Timestamp timestamp;
    public ResourceNotFoundException(String resourceName,String resourceId,Timestamp timestamp) {
        super(String.format("%s not found with id %s", resourceName,resourceId));
        this.resourceId = resourceId;
        this.timestamp = timestamp;
    }


}
