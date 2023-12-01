package com.example.demotest;

import com.example.demotest.exceptions.UserAlreadyExistException;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import com.example.demotest.service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegisterServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponderRepository responderRepository;

    @Mock
    private User user;

    @Mock
    private Responder responder;

    private RegisterService registerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        registerService = new RegisterService(userRepository, responderRepository);
        when(user.getName()).thenReturn("username").thenReturn("username");
        when(responder.getName()).thenReturn("username").thenReturn("username");
    }

    @Test
    public void addUserSuccess() {
        when(userRepository.existsById("username")).thenReturn(false);
        when(responderRepository.existsById("username")).thenReturn(false);
        when(userRepository.save(any())).thenReturn(null);
        registerService.addUser(user);
    }

    @Test
    public void addUserFailed() {
        try {
            when(userRepository.existsById("username")).thenReturn(true);
            when(responderRepository.existsById("username")).thenReturn(true);
            registerService.addUser(user);
            fail();
        } catch (UserAlreadyExistException ignored) {

        }
    }

    @Test
    public void addResponderSuccess() {
        when(userRepository.existsById("username")).thenReturn(false);
        when(responderRepository.existsById("username")).thenReturn(false);
        when(responderRepository.save(any())).thenReturn(null);
        registerService.addResponder(responder);
    }

    @Test
    public void addResponderFailed() {
        try {
            when(userRepository.existsById("username")).thenReturn(true);
            when(responderRepository.existsById("username")).thenReturn(true);
            registerService.addResponder(responder);
            fail();
        } catch (UserAlreadyExistException ignored) {

        }
    }
}
