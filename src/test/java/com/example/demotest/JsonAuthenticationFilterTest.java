package com.example.demotest;
import com.example.demotest.filter.JsonAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JsonAuthenticationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    private JsonAuthenticationFilter jsonAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        jsonAuthenticationFilter = new JsonAuthenticationFilter(authenticationManager);
    }

    @Test
    public void testAttemptAuthentication() throws Exception {

        String jsonPayload = "{\"username\":\"testUser\", \"password\":\"testPass\"}";
        InputStream jsonStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(jsonPayload.getBytes(StandardCharsets.UTF_8));

        Authentication mockAuthentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);

        Authentication result = jsonAuthenticationFilter.attemptAuthentication(request, null);

        assertNotNull(result, "Authentication result should not be null.");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}