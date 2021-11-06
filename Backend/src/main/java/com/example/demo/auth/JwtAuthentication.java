package com.example.demo.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;


public class JwtAuthentication extends AbstractAuthenticationToken {

    @Getter
    private final long id;

    @Getter
    private final String userId;

    @Getter
    private final String name;

    @Getter
    private final String email;

    public JwtAuthentication(
            long id,
            String userId,
            String name,
            String email,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
