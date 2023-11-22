package com.example.demotest.exceptions;

public class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException(String message) {
        super(message);
    }
}
