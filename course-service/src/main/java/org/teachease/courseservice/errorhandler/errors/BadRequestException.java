package org.teachease.courseservice.errorhandler.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class BadRequestException extends RuntimeException {
    private String errorMessage;

    private Timestamp timestamp;
    public BadRequestException(String errorMessage,Timestamp timestamp) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }
    public BadRequestException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

}
