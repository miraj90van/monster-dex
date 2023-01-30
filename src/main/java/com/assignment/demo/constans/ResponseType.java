package com.assignment.demo.constans;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseType {

    SUCCESS(HttpStatus.OK, "Ok"),

    SUCCESS_CAPTURE_MONSTER(HttpStatus.CREATED, "Success capture monster"),
    SUCCESS_CREATE_USER(HttpStatus.CREATED, "Success create user"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Something when wrong"),

    INVALID(HttpStatus.BAD_REQUEST, "Invalid"),

    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Username already exists"),

    LOCK_TRANSACTION(HttpStatus.UNPROCESSABLE_ENTITY, "Transaction Still Progress");


    private final HttpStatus httpStatus;
    private final String message;

    ResponseType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}