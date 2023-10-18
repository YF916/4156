package com.example.demotest;

import com.example.demotest.controller.UserController;
import com.example.demotest.model.User;
import com.example.demotest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewUser_Success() {
        User user = new User();
        user.setName("John Doe");
        user.setPhone("1234567890");

        when(userRepository.save(user)).thenReturn(user);

        String response = userController.addNewUser(user);
        assertEquals("Saved", response);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testAddNewUser_Failure() {
        User user = new User();
        user.setName("John Doe");
        user.setPhone("1234567890");

        when(userRepository.save(user)).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> userController.addNewUser(user));
        verify(userRepository, times(1)).save(user);
    }
}

