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

import java.util.ArrayList;
import java.util.List;

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
    @Test
    public void testAddNewUser_MissingInfo() {
        User user = new User(); // Not setting any information on the user.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.addNewUser(user);
        });

        // check that save was never called due to invalid input.
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testGetAllUsers_Success() {
        List<User> users = new ArrayList<>();

        User jane = new User();
        jane.setName("Jane Doe");
        jane.setPhone("1234567890");
        users.add(jane);

        User john = new User();
        john.setName("John Smith");
        john.setPhone("0987654321");
        users.add(john);

        when(userRepository.findAll()).thenReturn(users);
        Iterable<User> response = userController.getAllUsers();
        assertIterableEquals(users, response);
        verify(userRepository, times(1)).findAll();
    }
    @Test
    public void testAddNewUser_NullUser() {
        Exception exception = assertThrows(Exception.class, () -> {
            userController.addNewUser(null);
        });
        verify(userRepository, times(0)).save(any(User.class));
    }
}

