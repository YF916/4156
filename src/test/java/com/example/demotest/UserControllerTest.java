package com.example.demotest;

import com.example.demotest.controller.UserController;
import com.example.demotest.exceptions.InvalidRequestException;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private DispatchHistoryRepository dispatchHistoryRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendDispatchRequest_Success() {
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findById("testUser")).thenReturn(Optional.of(new User()));
        when(dispatchHistoryRepository.findByCallerAndStatusNot(any(User.class), eq("finished")))
                .thenReturn(Collections.emptyList());

        DispatchHistory history = new DispatchHistory();
        history.setStatus("pending");
        history.setStartTime(LocalDateTime.now());

        String result = userController.sendDispatchRequest(userDetails, history);

        assertEquals("request submitted", result);
        verify(dispatchHistoryRepository).save(any(DispatchHistory.class));
    }

    @Test
    public void testSendDispatchRequest_ActiveRequestExists() {
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findById("testUser")).thenReturn(Optional.of(new User()));
        when(dispatchHistoryRepository.findByCallerAndStatusNot(any(User.class), eq("finished")))
                .thenReturn(Collections.singletonList(new DispatchHistory()));

        DispatchHistory history = new DispatchHistory();

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            userController.sendDispatchRequest(userDetails, history);
        });

        String expectedMessage = "you already have an active request";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        Iterable<User> result = userController.getAllUsers();

        assertNotNull(result);
        verify(userRepository).findAll();
    }



    @Test
    public void testSetPassword() {
        Responder responder = new Responder();
        String testPassword = "TestPassword123";
        responder.setPassword(testPassword);

        assertEquals(testPassword, responder.getPassword(), "Password should be set correctly.");
    }

    @Test
    public void testSetPhone() {
        Responder responder = new Responder();
        String testPhone = "1234567890";
        responder.setPhone(testPhone);

        assertEquals(testPhone, responder.getPhone(), "Phone number should be set correctly.");
    }

    @Test
    public void testSetLongitude() {
        Responder responder = new Responder();
        Double testLongitude = 40.7128;
        responder.setLongitude(testLongitude);

        assertEquals(testLongitude, responder.getLongitude(), "Longitude should be set correctly.");
    }

    @Test
    public void testSetLatitude() {
        Responder responder = new Responder();
        Double testLatitude = -74.0060;
        responder.setLatitude(testLatitude);

        assertEquals(testLatitude, responder.getLatitude(), "Latitude should be set correctly.");
    }

    @Test
    public void testSetStatus() {
        Responder responder = new Responder();
        String testStatus = "active";
        responder.setStatus(testStatus);

        assertEquals(testStatus, responder.getStatus(), "Status should be set correctly.");
    }

}

