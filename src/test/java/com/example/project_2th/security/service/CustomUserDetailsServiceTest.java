package com.example.project_2th.security.service;

import com.example.project_2th.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class CustomUserDetailsServiceTest implements UserDetailsService {
    public static final String USERNAME = "010-1234-5678";

    private User getUser() {
        return User.builder().userName("김화순").loginNumber("1234").userPhone("010-1234-5678")
                .userBirthdate(LocalDate.parse("1963-07-16")).userExpireDate(LocalDate.parse("2022-08-20"))
                .managerYn(0).videoYn(1).userGym("해운대").role("ROLE_ADMIN").build();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> roles= new ArrayList<>();

        roles.add(new SimpleGrantedAuthority(getUser().getRole()));

        if (s.equals(USERNAME)) {
            return new UserContext(getUser(),roles);
        }
        return null;
    }
}
