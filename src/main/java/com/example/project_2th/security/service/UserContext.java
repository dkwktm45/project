package com.example.project_2th.security.service;

import com.example.project_2th.response.UserResponse;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.util.Collection;

@Getter
public class UserContext extends User implements UserDetails{

    private final com.example.project_2th.entity.User user;

    public UserContext(com.example.project_2th.entity.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserPhone(), user.getLoginNumber(), authorities);
        this.user = user;
    }

}
