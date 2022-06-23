package com.example.project_2th.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String conde;
    private final String message;

    private Map<String , String> validation = new HashMap<>();

    public void addVaildation(String field, String defaultMessage) {
        this.validation.put(field, defaultMessage);
    }
}
