package com.example.demotest;


import com.example.demotest.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomAuthenticationProviderTest {
    @Mock
    private UserDetails userDetails;
    @Mock
    private Authentication authentication;
    @Mock
    private UserDetailsServiceImpl userDetailsService;

    private CustomAuthenticationProvider customAuthenticationProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customAuthenticationProvider = new CustomAuthenticationProvider(userDetailsService);
    }

    @Test
    public void testPassWordCorrect() {
        when(authentication.getName()).thenReturn("username");
        when(authentication.getCredentials()).thenReturn("password");
        when(userDetails.getUsername()).thenReturn("username");
        when(userDetails.getPassword()).thenReturn("password");

        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        Authentication successAuth = customAuthenticationProvider.authenticate(authentication);
        assertNotNull(successAuth);
    }

    @Test
    public void testPassWordInCorrect() {
        when(authentication.getName()).thenReturn("username");
        when(authentication.getCredentials()).thenReturn("password");
        when(userDetails.getUsername()).thenReturn("username");
        when(userDetails.getPassword()).thenReturn("password1");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        try {
            Authentication failAuth = customAuthenticationProvider.authenticate(authentication);
            fail();
        } catch (BadCredentialsException ignored) {
        }
    }
}
