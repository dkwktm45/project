package com.example.project_2th.controller;

import com.example.project_2th.exception.SuperException;
import com.example.project_2th.response.ErrorResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
