package com.example.project_2th.security.mock;

import com.example.project_2th.entity.User;
import com.example.project_2th.security.service.UserContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDate;
import java.util.Arrays;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser>  {


    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {


        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        User user = User.builder().userName(annotation.username()).loginNumber("1234").userPhone("010-2345-1234")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").role(annotation.role()).build();
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user,
                "password",
                Arrays.asList(new SimpleGrantedAuthority(annotation.role())));

        UserContext userContext = new UserContext(user,Arrays.asList(new SimpleGrantedAuthority(annotation.role())));
        authenticationToken.setDetails(userContext);
        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
