package com.example.demotest;

import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.demotest.repository.UserRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.service.UserDetailsServiceImpl;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponderRepository responderRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void loadUserByUsername_UserFound() {

        String username = "testUser";
        User mockUser = new User();
        mockUser.setName(username);
        mockUser.setPassword("password");
        when(userRepository.findById(username)).thenReturn(Optional.of(mockUser));
        when(responderRepository.findById(username)).thenReturn(Optional.empty());


        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals(username, userDetails.getUsername(), "Username should match");
        assertEquals("password", userDetails.getPassword(), "Password should match");
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("user")), "Authority should be 'user'");
    }

    @Test
    public void loadUserByUsername_ResponderFound() {

        String username = "testResponder";
        Responder mockResponder = new Responder();
        mockResponder.setName(username);
        mockResponder.setPassword("password");
        when(userRepository.findById(username)).thenReturn(Optional.empty());
        when(responderRepository.findById(username)).thenReturn(Optional.of(mockResponder));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals(username, userDetails.getUsername(), "Username should match");
        assertEquals("password", userDetails.getPassword(), "Password should match");
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("responder")), "Authority should be 'responder'");
    }

    @Test
    public void loadUserByUsername_UserNotFound() {

        String username = "unknownUser";
        when(userRepository.findById(username)).thenReturn(Optional.empty());
        when(responderRepository.findById(username)).thenReturn(Optional.empty());


        assertThrows(UsernameNotFoundException.class, () -> {
            // When
            userDetailsService.loadUserByUsername(username);
        }, "UsernameNotFoundException should be thrown");
    }
}
