package com.example.demo.api.exception;

public class AuthException extends Exception {

    public AuthException(String string) {
        super(string);
    }

    public AuthException(String string, Throwable  t) {
        super(string, t);
    }

    public AuthException(Throwable t) {
        super(t);
    }
}
