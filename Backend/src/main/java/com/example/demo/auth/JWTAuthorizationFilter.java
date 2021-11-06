package com.example.demo.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.demo.api.exception.AuthException;
import com.example.demo.util.Constraints;

@Component
public class JWTAuthorizationFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private final JwtTokenProcessor jwtTokenProcessor;

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Value("${spring.security.cookie.domain}")
    private String cookieDomain;

    @Autowired
    public JWTAuthorizationFilter(
            HandlerExceptionResolver handlerExceptionResolver,
            JwtTokenProcessor jwtTokenProcessor) {
        this.handlerExceptionResolver= handlerExceptionResolver;
        this.jwtTokenProcessor = jwtTokenProcessor;
    }

    @Override
    public final void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        try {
            String url = req.getRequestURI();
            log.debug("AuthFilter: {}", url);
            Cookie[] cookies = req.getCookies();
            if (cookies == null || cookies.length == 0) {
                log.debug("No cookie in rrequest header");
            } else {
                for (Cookie c : cookies) {
                    log.debug("name: {}, value = {}", c.getName(), c.getValue());
                }
            }
            Authentication authentication = this.jwtTokenProcessor.authenticate(req);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().startsWith(Constraints.COOKIE_ID_TOKEN_KEY)) {
                        cookie.setPath("/");
                        cookie.setDomain(cookieDomain);
                        cookie.setMaxAge(0);
                        res.addCookie(cookie);
                        log.debug("delete cookie: {}", cookie.getName());
                    }
                }
            }
            handlerExceptionResolver.resolveException(
                    req, res, null, new AuthException("もう一度ログインしてください", exception));
        }
        filterChain.doFilter(request, response);
    }
}
