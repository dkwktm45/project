package com.example.project_2th.controller;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.exception.SuperException;
import com.example.project_2th.response.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(SuperException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handle404(SuperException e){
        int statusCode = e.statusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> errorResponse = ResponseEntity
                .status(statusCode)
                .body(body);
        return errorResponse;
    }
}
