package com.example.demotest;

import com.example.demotest.controller.CustomExceptionHandler;
import com.example.demotest.exceptions.InvalidRequestException;
import com.example.demotest.exceptions.NoSuchAccountException;
import com.example.demotest.exceptions.RequestAlreadyHandledException;
import com.example.demotest.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExceptionHandlerTest {

    private final CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    public void whenRequestAlreadyHandledException_thenConflictResponse() {
        RequestAlreadyHandledException exception = new RequestAlreadyHandledException("Request already handled");
        ResponseEntity<String> response = customExceptionHandler.handleRequestAlreadyHandledException(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Request already handled", response.getBody());
    }

    @Test
    public void whenUserAlreadyExistException_thenConflictResponse() {
        UserAlreadyExistException exception = new UserAlreadyExistException("User already exists");
        ResponseEntity<String> response = customExceptionHandler.handleUserAlreadyExistExceptions(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    public void whenInvalidRequestException_thenBadRequestResponse() {
        InvalidRequestException exception = new InvalidRequestException("Invalid request");
        ResponseEntity<String> response = customExceptionHandler.handleInvalidRequestException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request", response.getBody());
    }

    @Test
    public void whenNoSuchAccountException_thenUnauthorizedResponse() {
        NoSuchAccountException exception = new NoSuchAccountException("No such account");
        ResponseEntity<String> response = customExceptionHandler.handleNoSuchAccountException(exception);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("No such account", response.getBody());
    }

    @Test
    public void whenConstraintViolationException_thenBadRequestResponse() {
        ConstraintViolationException exception = new ConstraintViolationException(null);
        ResponseEntity<String> response = customExceptionHandler.handleConstraintViolationException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid request format, please check the api documentation", response.getBody());
    }
}
