package com.example.project_2th.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserContext extends User implements UserDetails{

    private final com.example.project_2th.entity.User user;

    public UserContext(com.example.project_2th.entity.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserPhone(), user.getLoginNumber(), authorities);
        this.user = user;
    }

    public com.example.project_2th.entity.User getUser() {
        return user;
    }
}
