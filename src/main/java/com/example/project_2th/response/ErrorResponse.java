package com.example.project_2th.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final Map<String , String> validation;
    private final String path;

    @Builder
    public ErrorResponse(String code,String message,Map<String , String> validation,String path){
        this.code = code;
        this.message = message;
        this.validation = validation;
        this.path = path;
    }
}
