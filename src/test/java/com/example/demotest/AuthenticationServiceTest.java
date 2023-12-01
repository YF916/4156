package com.example.demotest;

import com.example.demotest.exceptions.NoSuchAccountException;
import com.example.demotest.service.AuthenticationService;
import com.example.demotest.service.RegisterService;
import com.example.demotest.util.JwtUtil;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.Mock;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication auth;

    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(authenticationManager, jwtUtil);
    }

    @Test
    public void testNoAuth() {
        try {
            when(authenticationManager.authenticate(any())).thenReturn(null);
            authenticationService.authenticateUser("user", "pass", "role");
            fail();
        } catch (NoSuchAccountException e) {

        }
    }

    @Test
    public void testFailedAuth() {
        when(auth.isAuthenticated()).thenReturn(false);
        try {
            when(authenticationManager.authenticate(any())).thenReturn(auth);
            authenticationService.authenticateUser("user", "pass", "role");
            fail();
        } catch (NoSuchAccountException e) {

        }
    }

    @Test
    public void testEmptyAuth() {
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getAuthorities()).thenReturn(Collections.emptyList());
        try {
            when(authenticationManager.authenticate(any())).thenReturn(auth);
            authenticationService.authenticateUser("user", "pass", "role");
            fail();
        } catch (NoSuchAccountException e) {

        }
    }
}
