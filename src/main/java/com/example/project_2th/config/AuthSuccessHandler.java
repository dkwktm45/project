package com.example.project_2th.config;

import com.example.project_2th.entity.User;
import com.example.project_2th.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String gym = request.getParameter("userGym");
        String number = request.getParameter("loginNumber");
        User loginUser = userRepository.findByLoginNumberAndUserGym(number,gym);
        request.getSession().setAttribute("user", loginUser);
        response.sendRedirect("main");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
