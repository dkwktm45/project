package com.example.project_2th.controller;

import com.example.project_2th.response.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse inVaildRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse response = new ErrorResponse("400","잘못된 요청입니다.");
        for(FieldError fieldError:e.getFieldErrors()){
            response.addVaildation(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseBody
    public ErrorResponse handleSQLException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("500", "DB 접속 오류가 발생했습니다. DB정보를 다시 확인해주세요.");

        return errorResponse;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handle404(NoHandlerFoundException e){
        ErrorResponse errorResponse = new ErrorResponse("404", "페이지를 찾을 수 없습니다.");
        return errorResponse;
    }

}
