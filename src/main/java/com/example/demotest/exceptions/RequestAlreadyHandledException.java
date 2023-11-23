package com.example.demotest.exceptions;

public class RequestAlreadyHandledException extends RuntimeException {
    public RequestAlreadyHandledException(String message) {
        super(message);
    }
}
