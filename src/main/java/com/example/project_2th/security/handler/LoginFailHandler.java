package com.example.project_2th.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 인증에 대한 실패가 발생할 때 발생!
        String errorMessage = "Invalid UserName or password";

        if(exception instanceof BadCredentialsException){
            errorMessage = "Invalid UserName or password";
        }else if (exception instanceof InsufficientAuthenticationException){
            errorMessage = "Invalid Secret Key";
        }
        response.sendRedirect("/user/login?error=true&exception=" + exception.getMessage());
    }
}
