package com.example.project_2th.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

public enum Authority implements GrantedAuthority{

    USER("ROLE_USER", "유저권한"),
    ADMIN("ROLE_ADMIN", "어드민권한");

    private String authority;
    private String description;

    private Authority(String authority, String description){
        this.authority = authority;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getDescription() {
        return description;
    }

}
