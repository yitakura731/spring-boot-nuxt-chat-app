package com.example.demo.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class LoggingFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public final void doFilter(
            ServletRequest req,
            ServletResponse resp,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String method = request.getMethod();
        String url = request.getRequestURI();
        log.debug("{}, start: {}", method, url);
        filterChain.doFilter(request, response);
        log.info("{}, end: {}", method, url);
    }
}
