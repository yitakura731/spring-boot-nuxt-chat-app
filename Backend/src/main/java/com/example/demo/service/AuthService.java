package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.api.data.AppUser;
import com.example.demo.api.exception.AuthException;
import com.example.demo.auth.JwtAuthentication;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.data.BaseUser;
import com.example.demo.util.Tools;

@Service
public class AuthService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${proxy.host:}")
    private String proxyHost;

    @Value("${proxy.port:0}")
    private int proxyPort;

    private final UserRepository repository;

    public AuthService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            return this.repository.findByUserId(userId);
        } catch(Exception exception) {
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }

    public AppUser signIn(String userId, String password) throws Exception {
        log.debug("signin service start: {}", userId);
        if (userId == null || userId.isEmpty() || userId.isBlank() ||
                password == null || password.isEmpty() || password.isBlank()) {
            throw new AuthException("ユーザー名またはパスワードが未指定です");
        }
        BaseUser user = (BaseUser)loadUserByUsername(userId);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthException("ユーザー名またはパスワードが異なります");
        }
        return Tools.toAppUser(user);
    }

    public AppUser getUser(String userId) {
        BaseUser user = (BaseUser)loadUserByUsername(userId);
        return Tools.toAppUser(user);
    }

    public AppUser getCurrentUser() {
        JwtAuthentication auth = getAuthInfoInternal();
        if (auth != null) {
            return Tools.toAppUser(auth);
        } else {
            return null;
        }
    }

    public void signOut() throws Exception {
    }

    private JwtAuthentication getAuthInfoInternal() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth instanceof JwtAuthentication) {
            return (JwtAuthentication) auth;
        } else {
            return null;
        }
    }
}