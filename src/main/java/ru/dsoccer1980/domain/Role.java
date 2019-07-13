package ru.dsoccer1980.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, ANONYM;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
