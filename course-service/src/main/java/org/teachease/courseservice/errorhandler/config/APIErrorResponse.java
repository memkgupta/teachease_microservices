package org.teachease.courseservice.errorhandler.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class APIErrorResponse {
    private String message;
    private String path;
    private String error;
    private Integer status;
    private Timestamp timestamp;
}
