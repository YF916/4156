package com.example.demotest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.example.demotest.controller.AuthenticationController;
import com.example.demotest.exceptions.InvalidRequestException;
import com.example.demotest.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void testAuthenticateUser_Success() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("password", "testPass");

        when(authenticationService.authenticateUser("testUser", "testPass", "user")).thenReturn("Success");

        String result = authenticationController.authenticateUser(requestBody);

        assertEquals("Success", result);
        verify(authenticationService).authenticateUser("testUser", "testPass", "user");
    }

    @Test
    public void testAuthenticateUser_MissingPassword() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        // Missing password

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            authenticationController.authenticateUser(requestBody);
        });

        String expectedMessage = "request body should have username and password";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAuthenticateUser_MissingUsername() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("password", "testPass");
        // Missing username

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            authenticationController.authenticateUser(requestBody);
        });

        String expectedMessage = "request body should have username and password";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    // Similar tests for authenticateResponder endpoint

    @Test
    public void testAuthenticateResponder_Success() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testResponder");
        requestBody.put("password", "testPass");

        when(authenticationService.authenticateUser("testResponder", "testPass", "responder")).thenReturn("Success");

        String result = authenticationController.authenticateResponder(requestBody);

        assertEquals("Success", result);
        verify(authenticationService).authenticateUser("testResponder", "testPass", "responder");
    }

    @Test
    public void testAuthenticateResponder_MissingPassword() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testResponder");
        // Missing password

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            authenticationController.authenticateUser(requestBody);
        });

        String expectedMessage = "request body should have username and password";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAuthenticateResponder_MissingUsername() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("password", "testPass");
        // Missing username

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            authenticationController.authenticateUser(requestBody);
        });

        String expectedMessage = "request body should have username and password";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

