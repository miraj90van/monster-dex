package com.assignment.demo.helper;

import com.assignment.demo.entity.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseHelper<T> {

    public static <T> ResponseEntity<T> getResponseData(BaseResponse<T> baseResponse){
        return new ResponseEntity<>(baseResponse.getData(), baseResponse.getHttpStatus());
    }

    public static <T> ResponseEntity<String> getResponseMessage(BaseResponse<T> baseResponse){
        return new ResponseEntity<>(baseResponse.getMessage(), baseResponse.getHttpStatus());
    }

    public static <T> ResponseEntity<String> getResponseError(HttpStatus httpStatus, String message){
        return new ResponseEntity<>(message, httpStatus);
    }
}