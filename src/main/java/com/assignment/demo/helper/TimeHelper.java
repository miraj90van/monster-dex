package com.assignment.demo.helper;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TimeHelper {

    public static LocalDateTime getLocalDateTimeNow(){
        return LocalDateTime.now();
    }

}
