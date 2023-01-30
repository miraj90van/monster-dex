package com.assignment.demo.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();
    public static <T> T objectMapper(Object obj, Class<T> contentClass){
        return mapper.convertValue(obj, contentClass);
    }
}