package org.teachease.courseservice.errorhandler.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@Builder
public class InternalServerError extends RuntimeException {
        private String errorMessage;
        private String stackTrace;
        private Timestamp timestamp;

        public InternalServerError(String errorMessage, String stackTrace, Timestamp timestamp) {
            this.errorMessage = errorMessage;
            this.stackTrace = stackTrace;
            this.timestamp = timestamp;
        }
        public InternalServerError(String errorMessage, String stackTrace) {
            this.errorMessage = errorMessage;
            this.stackTrace = stackTrace;
            this.timestamp = new Timestamp(System.currentTimeMillis());
        }


}
