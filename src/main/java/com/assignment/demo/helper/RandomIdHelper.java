package com.assignment.demo.helper;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RandomIdHelper {
    private static final String SEPARATOR = "-";
    public static String getRandomId(){
        return UUID.randomUUID() + SEPARATOR + System.currentTimeMillis();
    }
}