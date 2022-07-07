package com.example.project_2th.controller;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.exception.SuperException;
import com.example.project_2th.response.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(SuperException.class)
    public String handle404(SuperException e,Model model){
        int statusCode = e.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
        model.addAttribute("error",body);
        return "redirect:/error";
    }
}
