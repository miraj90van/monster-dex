package com.assignment.demo.exceptions;

import com.assignment.demo.constans.ResponseType;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public class BusinessLogicException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;
    private final String message;

    public BusinessLogicException(ResponseType responseType) {
        super();
        this.httpStatus = responseType.getHttpStatus();
        this.message = responseType.getMessage();
    }
}