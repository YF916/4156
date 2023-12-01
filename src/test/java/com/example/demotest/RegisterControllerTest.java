package com.example.demotest;

import com.example.demotest.controller.RegisterController;
import com.example.demotest.exceptions.UserAlreadyExistException;
import com.example.demotest.service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.service.AuthenticationService;
import com.example.demotest.service.RegisterService;

import com.example.demotest.controller.AuthenticationController;
import com.example.demotest.controller.RegisterController;
import com.example.demotest.exceptions.InvalidRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    public void testAddUser() {
        User user = new User(); // Assuming User is a valid class
        // Populate user with test data if necessary

        registerController.addUser(user);

        verify(registerService).addUser(user);
    }

    @Test
    public void testAddResponder() {
        Responder responder = new Responder(); // Assuming Responder is a valid class
        // Populate responder with test data if necessary

        registerController.addResponder(responder);

        verify(registerService).addResponder(responder);
    }



}
