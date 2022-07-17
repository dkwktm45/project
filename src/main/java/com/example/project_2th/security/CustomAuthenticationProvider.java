package com.example.project_2th.security;

import com.example.project_2th.exception.PostNotFound;
import com.example.project_2th.repository.UserRepository;
import com.example.project_2th.security.common.FormWebAuthenticationDetails;
import com.example.project_2th.security.service.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 검증이 구현
        String userPhone = authentication.getName();
        String password = (String)authentication.getCredentials();//4903

        UserContext userContext = (UserContext) userDetailsService.loadUserByUsername(userPhone);

        if(!passwordEncoder.matches(password, userContext.getUser().getLoginNumber())){
            throw new BadCredentialsException("badCredentialsException");
        }

        String secretKey = ((FormWebAuthenticationDetails) authentication.getDetails()).getSecretKey();
        if (secretKey ==null || !secretKey.equals("secret")){
            throw new InsufficientAuthenticationException("Invalid Secret");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userContext.getUser(),null,userContext.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // custom 과 동일한지 확인
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
