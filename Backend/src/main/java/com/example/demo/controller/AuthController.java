package com.example.demo.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.data.AppUser;
import com.example.demo.auth.JwtTokenProcessor;
import com.example.demo.service.AuthService;
import com.example.demo.util.Constraints;
import com.example.demo.util.Tools;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Value("${spring.security.cookie.domain}")
    private String cookieDomain;

    @Value("${spring.security.cookie.secure}")
    private String cookieSecure;

    private final AuthService auth;

    private final JwtTokenProcessor jwtProcessor;

    @Autowired
    public AuthController(
            AuthService auth,
            JwtTokenProcessor jwtProcessor) {
        this.auth = auth;
        this.jwtProcessor = jwtProcessor;
    }

    @PostMapping("/signin")
    public AppUser signIn(
            @RequestBody Map<String, Object> request,
            HttpServletResponse response) throws Exception {
        log.debug("signin controller start");
        log.debug("Cookie: domain={}, secure={}", cookieDomain, cookieSecure);
        AppUser appUser = auth.signIn(
                (String) request.get("userId"), (String) request.get("passwd"));
        String token = jwtProcessor.createToken(appUser);
        response.addHeader("Set-Cookie", Tools.createCookie(
                Constraints.COOKIE_ID_TOKEN_KEY,
                token,
                cookieDomain,
                Boolean.valueOf(cookieSecure),
                false).toString());
        log.debug("Set-Cookie: {}", response.getHeader("Set-Cookie"));
        return appUser;
    }

    @GetMapping("/me")
    public AppUser getUser() throws Exception {
        AppUser user = auth.getCurrentUser();
        if (user == null) {
            return null;
        }
        return AppUser.builder().
                id(user.getId()).
                userId(user.getUserId()).
                name(user.getName()).
                email(user.getEmail()).
                build();
    }

    @PostMapping("/signout")
    public void signOut(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        auth.signOut();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().startsWith(Constraints.COOKIE_ID_TOKEN_KEY) ||
                    cookie.getName().equals(Constraints.COOKIE_CSRF_KEY)) {
                cookie.setDomain(cookieDomain);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                log.debug("delete cookie: {}", cookie.getName());
            }
        }
    }
}
