package com.example.demo.api.exception;

public class ServiceException extends Exception {

    public ServiceException(String string) {
        super(string);
    }

    public ServiceException(String string, Throwable  t) {
        super(string, t);
    }

    public ServiceException(Throwable t) {
        super(t);
    }
}
