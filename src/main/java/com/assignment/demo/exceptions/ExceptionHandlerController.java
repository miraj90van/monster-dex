package com.assignment.demo.exceptions;

import com.assignment.demo.helper.ResponseHelper;
import com.assignment.demo.security.auth.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> unauthorizedException(UnauthorizedException e) {
        log.error("unauthorizedException reason: {}", e.getMessage(), e);
        return ResponseHelper.getResponseError(HttpStatus.UNAUTHORIZED, "User token not valid");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> runTimeException(RuntimeException e) {
        log.error("runTimeException reason: {}", e.getMessage(), e);
        return ResponseHelper.getResponseError(HttpStatus.INTERNAL_SERVER_ERROR, "Something when wrong");
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> webExchangeBindException(WebExchangeBindException e) {
        log.error("webExchangeBindException reason: {}", e.getMessage(), e);
        return ResponseHelper.getResponseError(HttpStatus.BAD_REQUEST, "Bad Request");
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<String> businessLogicException(BusinessLogicException e) {
        log.warn("businessLogicException reason: {}", e.getMessage(), e);
        return ResponseHelper.getResponseError(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> accessDeniedException(AccessDeniedException e) {
        log.error("accessDeniedException reason: {}", e.getMessage(), e);
        return ResponseHelper.getResponseError(HttpStatus.UNAUTHORIZED, "User token not valid");
    }
}
