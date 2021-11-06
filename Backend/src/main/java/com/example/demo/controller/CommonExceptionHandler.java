package com.example.demo.controller;

import javax.security.auth.message.AuthException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.api.exception.ServiceException;

@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ServletRequestBindingException.class })
    @ResponseBody
    public String handleError(MissingPathVariableException exception, WebRequest req) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MissingRequiredPropertiesException.class })
    @ResponseBody
    public String handleError(MissingRequiredPropertiesException exception, WebRequest req) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ AuthException.class })
    @ResponseBody
    public String handleError(AuthException exception, WebRequest req) {
        String url = req.getContextPath();
        log.error("Request error: {}", url, exception);
        String msg = exception.getMessage();
        if (msg != null && !msg.isEmpty()) {
            return msg;
        } else {
            return "もう一度認証してください";
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ ServiceException.class })
    @ResponseBody
    public String handleError(ServiceException exception, WebRequest req) {
        String url = ((ServletWebRequest)req).getRequest().getRequestURI();
        log.error("Request error: {}", url, exception);
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ DbActionExecutionException.class })
    @ResponseBody
    public String handleError(DbActionExecutionException exception, WebRequest req) {
        String url = ((ServletWebRequest)req).getRequest().getRequestURI();
        log.error("Request error: {}", url, exception);
        return "サーバーでエラー発生しました";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public String handleError(Exception exception, WebRequest req) {
        String url = ((ServletWebRequest)req).getRequest().getRequestURI();
        log.error("Request error: {}", url, exception);
        return "サーバーでエラー発生しました";
    }
}
