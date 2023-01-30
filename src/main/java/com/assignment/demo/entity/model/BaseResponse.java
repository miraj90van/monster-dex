package com.assignment.demo.entity.model;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.helper.TimeHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseResponse <T> implements Serializable {
    private HttpStatus httpStatus;
    private String message;
    private T data;
    private LocalDateTime serverTime = TimeHelper.getLocalDateTimeNow();

    public BaseResponse(ResponseType type){
        this.httpStatus = type.getHttpStatus();
        this.message = type.getMessage();
    }

    public BaseResponse(ResponseType type, T obj){
        this.httpStatus = type.getHttpStatus();
        this.message = type.getMessage();
        this.data = obj;
    }

}