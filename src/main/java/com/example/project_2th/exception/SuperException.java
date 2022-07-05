package com.example.project_2th.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class SuperException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();

    public SuperException(String message){
        super(message);
    }
    public SuperException(String message, Throwable cause){
        super(message,cause);
    }
    public abstract int statusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName,message);
    }
}
