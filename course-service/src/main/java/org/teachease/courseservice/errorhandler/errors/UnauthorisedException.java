package org.teachease.courseservice.errorhandler.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@Builder
public class UnauthorisedException extends RuntimeException {
    private String resourceName;
    private String resourceId;
    private String action;

    public UnauthorisedException(String resourceName, String resourceId, String action) {
        super(String.format("Not authorized to perform %s on resource %s.%s ",action,resourceName,resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.action = action;

    }



}
