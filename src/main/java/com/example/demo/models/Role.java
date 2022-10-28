package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    USER, ADMIN, AVTO_MODERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
