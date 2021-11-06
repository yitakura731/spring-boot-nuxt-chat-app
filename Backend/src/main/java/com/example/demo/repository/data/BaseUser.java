package com.example.demo.repository.data;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("app_user")
public class BaseUser implements UserDetails {

    @Id
    @Column ("id")
    private final Long id;

    @Column ("user_id")
    private final String userId;

    @Column ("passwd")
    private final String passwd;

    @Column ("name")
    private final String name;

    @Column ("email")
    private String email;

    @Column ("auth")
    private String auth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(auth).
                map(SimpleGrantedAuthority::new).
                collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}