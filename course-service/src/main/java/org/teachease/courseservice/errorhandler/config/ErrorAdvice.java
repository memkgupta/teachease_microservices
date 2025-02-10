package org.teachease.courseservice.errorhandler.config;

import org.springframework.http.HttpStatus;
//import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.teachease.courseservice.config.RequestContext;
import org.teachease.courseservice.errorhandler.errors.BadRequestException;
import org.teachease.courseservice.errorhandler.errors.InternalServerError;
import org.teachease.courseservice.errorhandler.errors.ResourceNotFoundException;
import org.teachease.courseservice.errorhandler.errors.UnauthorisedException;

import java.sql.Timestamp;

@RestControllerAdvice
public class ErrorAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public APIErrorResponse handleBadRequestException(BadRequestException e) {

        return APIErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("BAD REQUEST")
                .timestamp(e.getTimestamp()!=null?e.getTimestamp():new Timestamp(System.currentTimeMillis()))
                .path(RequestContext.getRequestPath())

                    .build();
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerError.class)
  public APIErrorResponse handleInternalServerError(InternalServerError e) {
        return APIErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("INTERNAL SERVER ERROR")
                .timestamp(e.getTimestamp()!=null?e.getTimestamp():new Timestamp(System.currentTimeMillis()))
                .path(RequestContext.getRequestPath())
                .build();

    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public APIErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        return APIErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error("RESOURCE NOT FOUND")
                .timestamp(e.getTimestamp()!=null?e.getTimestamp():new Timestamp(System.currentTimeMillis()))
                .path(RequestContext.getRequestPath())
                .build();
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorisedException.class)
    public APIErrorResponse handleUnauthorisedException(UnauthorisedException e) {
        return APIErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .path(RequestContext.getRequestPath())
                .build();
    }
}
