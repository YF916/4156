package com.example.demotest;

import com.example.demotest.controller.DispatchHistoryController;
import com.example.demotest.exceptions.InvalidRequestException;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


import java.util.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.Set;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

//class TestUserDetails implements UserDetails {
//    private String username;
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public TestUserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
//        this.username = username;
//        this.authorities = authorities;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    // Implement other methods of UserDetails interface
//    // ...
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    // For simplicity, you can return true for other methods or implement as needed
//    @Override
//    public String getPassword() { return null; }
//    @Override
//    public boolean isAccountNonExpired() { return true; }
//    @Override
//    public boolean isAccountNonLocked() { return true; }
//    @Override
//    public boolean isCredentialsNonExpired() { return true; }
//    @Override
//    public boolean isEnabled() { return true; }
//}


@ExtendWith(MockitoExtension.class)
public class DispatchHistoryControllerTest {

    @Mock
    private DispatchHistoryRepository dispatchHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponderRepository responderRepository;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private DispatchHistoryController dispatchHistoryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRateDispatchHistory_NonExistentOrNotAccepted() {
        when(userDetails.getUsername()).thenReturn("userName");
        User user = new User(); // Assuming User has a default constructor
        when(userRepository.getReferenceById("userName")).thenReturn(user);
        when(dispatchHistoryRepository.existsByIdAndStatusNotAndCaller(1, "pending", user)).thenReturn(false);

        assertThrows(InvalidRequestException.class, () -> {
            dispatchHistoryController.rateDispatchHistory(userDetails, 1, 5.0, "Good service");
        });
    }

    @Test
    public void testRateDispatchHistory_AlreadyRated() {
        when(userDetails.getUsername()).thenReturn("userName");
        User user = new User(); // Assuming User has a default constructor
        when(userRepository.getReferenceById("userName")).thenReturn(user);
        DispatchHistory dispatchToRate = new DispatchHistory();
        dispatchToRate.setRating(4.0);
        when(dispatchHistoryRepository.existsByIdAndStatusNotAndCaller(1, "pending", user)).thenReturn(true);
        when(dispatchHistoryRepository.getReferenceById(1)).thenReturn(dispatchToRate);

        assertThrows(InvalidRequestException.class, () -> {
            dispatchHistoryController.rateDispatchHistory(userDetails, 1, 5.0, "Good service");
        });
    }

    @Test
    public void testRateDispatchHistory_SuccessWithFeedback() {
        when(userDetails.getUsername()).thenReturn("userName");
        User user = new User(); // Assuming User has a default constructor
        when(userRepository.getReferenceById("userName")).thenReturn(user);

        DispatchHistory dispatchToRate = new DispatchHistory();
        Responder responder = new Responder(); // Assuming Responder has a default constructor
        responder.setName("responderName");
        responder.setRating(4.5); // Initialize the rating
        dispatchToRate.setResponder(responder);

        when(dispatchHistoryRepository.existsByIdAndStatusNotAndCaller(1, "pending", user)).thenReturn(true);
        when(dispatchHistoryRepository.getReferenceById(1)).thenReturn(dispatchToRate);
        when(responderRepository.getReferenceById("responderName")).thenReturn(responder);

        String result = dispatchHistoryController.rateDispatchHistory(userDetails, 1, 5.0, "Good service");

        assertEquals("Rated", result);
        verify(dispatchHistoryRepository).save(dispatchToRate);
        assertEquals(5.0, dispatchToRate.getRating());
        assertEquals("Good service", dispatchToRate.getFeedback());
    }


    @Test
    public void testRateDispatchHistory_SuccessWithoutFeedback() {
        when(userDetails.getUsername()).thenReturn("userName");
        User user = new User(); // Assuming User has a default constructor
        when(userRepository.getReferenceById("userName")).thenReturn(user);

        DispatchHistory dispatchToRate = new DispatchHistory();
        Responder responder = new Responder(); // Assuming Responder has a default constructor
        responder.setName("responderName");
        responder.setRating(4.5); // Initialize the rating
        dispatchToRate.setResponder(responder);

        when(dispatchHistoryRepository.existsByIdAndStatusNotAndCaller(1, "pending", user)).thenReturn(true);
        when(dispatchHistoryRepository.getReferenceById(1)).thenReturn(dispatchToRate);
        when(responderRepository.getReferenceById("responderName")).thenReturn(responder);

        String result = dispatchHistoryController.rateDispatchHistory(userDetails, 1, 5.0, null);

        assertEquals("Rated", result);
        verify(dispatchHistoryRepository).save(dispatchToRate);
        assertEquals(5.0, dispatchToRate.getRating());
        assertNull(dispatchToRate.getFeedback());
    }

    @Test
    public void testUpdateArrivalTime_Success() {
        DispatchHistory dispatchToUpdate = new DispatchHistory();
        when(dispatchHistoryRepository.getReferenceById(1)).thenReturn(dispatchToUpdate);
        LocalDateTime arrivalTime = LocalDateTime.now();

        String result = dispatchHistoryController.updateArrivalTime(1, arrivalTime);

        assertEquals("Arrived", result);
        assertEquals(arrivalTime, dispatchToUpdate.getArrivalTime());
        assertEquals("arrived", dispatchToUpdate.getStatus());
        verify(dispatchHistoryRepository).save(dispatchToUpdate);
    }

    @Test
    public void testFinishDispatch_Success() {
        DispatchHistory dispatchToUpdate = new DispatchHistory();
        when(dispatchHistoryRepository.getReferenceById(1)).thenReturn(dispatchToUpdate);

        String result = dispatchHistoryController.finishDispatch(1);

        assertEquals("Finished", result);
        assertEquals("finished", dispatchToUpdate.getStatus());
        verify(dispatchHistoryRepository).save(dispatchToUpdate);
    }










//    private void setupUserDetailsWithAuthority(String authority, String username) {
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + authority.toUpperCase());
//        when(userDetails.getUsername()).thenReturn(username);
//        when(userDetails.getAuthorities()).thenReturn(Collections.singletonList(grantedAuthority));
//    }

//    private UserDetails createUserDetailsWithAuthority(String username, String role) {
//        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
//        User user = new User();
//        user.setName(username);
//        user.setPassword("password");
//
//        //username, "password", grantedAuthorities
//
//        UserDetails userDetails = (UserDetails) user;
//        userDetails.getAuthorities()
//        return spy(userDetails);
//    }
//
//    private UserDetails createUserDetailsWithNoAuthority(String username) {
//        UserDetails userDetails = new User(username, "password", Collections.emptyList());
//        return spy(userDetails);
//    }
//
//

//    private void setupUserDetailsWithAuthority(String username, String role) {
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
//        when(userDetails.getUsername()).thenReturn(username);
//        when(userDetails.getAuthorities()).thenReturn(Collections.singletonList(grantedAuthority));
//    }


    private void setupUserDetailsWithAuthority(String username, String role) {
        //GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
        when(userDetails.getUsername()).thenReturn(username);
        // when(userDetails.getAuthorities()).thenReturn(Collections.singletonList(grantedAuthority));
        when(userDetails.getAuthorities()).thenReturn((Collection) Collections.singletonList(new SimpleGrantedAuthority(role)));
        //when(userDetails.getAuthorities()).thenReturn(Collections.singletonList(grantedAuthority));
    }

    @Test
    public void testSearchDispatchHistory_UserRole() {
        setupUserDetailsWithAuthority("userUsername", "user");

        User user = new User(); // Assuming a constructor exists
        when(userRepository.getReferenceById("userUsername")).thenReturn(user);
        List<DispatchHistory> expectedDispatchHistories = new ArrayList<>();
        when(dispatchHistoryRepository.findByCallerAndStatus(user, "pending")).thenReturn(expectedDispatchHistories);

        Iterable<DispatchHistory> result = dispatchHistoryController.searchDispatchHistory(userDetails, "pending");

        assertEquals(expectedDispatchHistories, result);
        verify(dispatchHistoryRepository).findByCallerAndStatus(user, "pending"); // Verify the correct method call

    }

    @Test
    public void testSearchDispatchHistory_Responder() {
        setupUserDetailsWithAuthority("responderUsername", "responder");

        Responder responder = new Responder(); // Replace with your actual Responder constructor
        when(responderRepository.getReferenceById("responderUsername")).thenReturn(responder);
        when(dispatchHistoryRepository.findByResponderAndStatus(responder, "pending")).thenReturn(new ArrayList<>());

        Iterable<DispatchHistory> result = dispatchHistoryController.searchDispatchHistory(userDetails, "pending");

        assertNotNull(result);
        verify(dispatchHistoryRepository).findByResponderAndStatus(responder, "pending");
    }


//    @Test
//    public void testSearchDispatchHistory_UserRole() {
//        setupUserDetailsWithAuthority("user", "userUsername");
//
//        User user = new User(); // Assuming a constructor exists
//        when(userRepository.getReferenceById("userUsername")).thenReturn(user);
//        List<DispatchHistory> expectedDispatchHistories = new ArrayList<>();
//        when(dispatchHistoryRepository.findByCallerAndStatus(user, "pending")).thenReturn(expectedDispatchHistories);
//
//        Iterable<DispatchHistory> result = dispatchHistoryController.searchDispatchHistory(userDetails, "pending");
//
//        assertEquals(expectedDispatchHistories, result);
//        verify(dispatchHistoryRepository).findByCallerAndStatus(user, "pending");
//    }

    private void setupUserDetailsWithNoAuthority(String username) {
        when(userDetails.getUsername()).thenReturn(username);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
    }

    @Test
    public void testSearchDispatchHistory_AccessDenied() {
        setupUserDetailsWithNoAuthority("someUsername");

        assertThrows(AccessDeniedException.class, () -> {
            dispatchHistoryController.searchDispatchHistory(userDetails, "pending");
        });
    }








//    private void setupUserDetailsWithAuthority(String role) {
//        when(userDetails.getUsername()).thenReturn(role + "Username");
//
//        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
//        List<GrantedAuthority> authorities = Collections.singletonList(authority);
//
//        when(userDetails.getAuthorities()).thenReturn(authorities);
//    }
//
//    @Test
//    public void testSearchDispatchHistory_ResponderRole() {
//        setupUserDetailsWithAuthority("responder");
//        when(responderRepository.getReferenceById("responderUsername")).thenReturn(new Responder());
//        // Additional setup and assertions for fetching dispatch history as a responder
//    }
//
//    @Test
//    public void testSearchDispatchHistory_UserRole() {
//        setupUserDetailsWithAuthority("user");
//        when(userRepository.getReferenceById("userName")).thenReturn(new User());
//        // Additional setup and assertions for fetching dispatch history as a user
//    }
//
//    @Test
//    public void testSearchDispatchHistory_AccessDenied() {
//        setupUserDetailsWithAuthority("otherRole");
//        assertThrows(AccessDeniedException.class, () -> {
//            dispatchHistoryController.searchDispatchHistory(userDetails, "pending");
//        });
//    }
















//    @InjectMocks
//    private DispatchHistoryController dispatchHistoryController;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ResponderRepository responderRepository;
//
//    @Mock
//    private DispatchHistoryRepository dispatchHistoryRepository;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    public void testStartNewDispatch_NonExistentUserOrResponder() {
//        int userId = 999; // non-existent user
//        int responderId = 999; // non-existent responder
//        LocalDateTime startTime = LocalDateTime.now();
//
////        when(userRepository.getReferenceById(userId)).thenReturn(null);
////        when(responderRepository.getReferenceById(responderId)).thenReturn(null);
//
////        Exception exception = assertThrows(RuntimeException.class, () -> {
////            dispatchHistoryController.addNewDispatchHistory(userId, responderId, startTime);
////        });
////
////        assertNotNull(exception);
//        verify(dispatchHistoryRepository, times(0)).save(any(DispatchHistory.class));
//    }


//    @Test
//    public void testStartNewDispatch_Success() {
//        int userId = 1;
//        int responderId = 1;
//        LocalDateTime startTime = LocalDateTime.now();
//
//        when(userRepository.getReferenceById(userId)).thenReturn(new User());
//        when(responderRepository.getReferenceById(responderId)).thenReturn(new Responder());
//
//        String response = dispatchHistoryController.addNewDispatchHistory(userId, responderId, startTime);
//        assertEquals("Saved", response);
//        verify(dispatchHistoryRepository, times(1)).save(any(DispatchHistory.class));
//    }

//    @Test
//    public void testRateDispatch_Success() {
//        int dispatchId = 1;
//        Double rating = 5.0;
//        String feedback = "Great service!";
//        DispatchHistory mockDispatch = new DispatchHistory();
//
//        when(dispatchHistoryRepository.getReferenceById(dispatchId)).thenReturn(mockDispatch);
//
//        String response = dispatchHistoryController.rateDispatchHistory(dispatchId, rating, feedback);
//        assertEquals("Rated", response);
//        verify(dispatchHistoryRepository, times(1)).save(mockDispatch);
//    }

//    @Test
//    public void testUpdateArrivalTime_Success() {
//        int dispatchId = 1;
//        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(1);
//        DispatchHistory mockDispatch = new DispatchHistory();
//
//        when(dispatchHistoryRepository.getReferenceById(dispatchId)).thenReturn(mockDispatch);
//
//        String response = dispatchHistoryController.updateArrivalTime(dispatchId, arrivalTime);
//        assertEquals("Arrived", response);
//        verify(dispatchHistoryRepository, times(1)).save(mockDispatch);
//    }

//    @Test
//    public void testAddNewDispatchHistory_validInput_savesDispatchHistory() {
//        // Arrange
//        DispatchHistory dispatchHistory = new DispatchHistory();
//        when(userRepository.getReferenceById(anyInt())).thenReturn(new User());
//        when(responderRepository.getReferenceById(anyInt())).thenReturn(new Responder());
//
//        // Act
//        dispatchHistoryController.addNewDispatchHistory(1, 2, LocalDateTime.now());
//
//        // Assert
//        verify(dispatchHistoryRepository, times(1)).save(any(DispatchHistory.class));
//    }

//    @Test
//    public void testRateDispatchHistory_validInput_ratesDispatchHistory() throws Exception {
//        // Arrange
//        DispatchHistory dispatchHistory = new DispatchHistory();
//        when(dispatchHistoryRepository.getReferenceById(anyInt())).thenReturn(dispatchHistory);
//
//        // Act
//        dispatchHistoryController.rateDispatchHistory(1, 5.0, "Good service");
//
//        // Assert
//        assertEquals(5.0, dispatchHistory.getRating());
//        assertEquals("Good service", dispatchHistory.getFeedback());
//    }

//
//
//    @Test
//    public void testUpdateArrivalTime_validInput_updatesArrivalTime() {
//        // Arrange
//        DispatchHistory dispatchHistory = new DispatchHistory();
//        LocalDateTime arrivalTime = LocalDateTime.now();
//        when(dispatchHistoryRepository.getReferenceById(anyInt())).thenReturn(dispatchHistory);
//
//        // Act
//        dispatchHistoryController.updateArrivalTime(1, arrivalTime);
//
//        // Assert
//        assertEquals(arrivalTime, dispatchHistory.getArrivalTime());
//    }
//
//
//
//
//    @Test
//    public void testSearchDispatchHistory_noFilter_returnsAll() {
//        // Arrange
//        when(dispatchHistoryRepository.findAll()).thenReturn(new ArrayList<DispatchHistory>());
//
//        // Act
//        Iterable<DispatchHistory> results = dispatchHistoryController.searchDispatchHistory(null, null);
//
//        // Assert
//        assertNotNull(results);
//    }
//
//    @Test
//    public void testSearchDispatchHistory_validInput_returnsDispatchHistories() {
//        // Arrange
//        when(dispatchHistoryRepository.findAll()).thenReturn(new ArrayList<DispatchHistory>());
//
//        // Act
//        Iterable<DispatchHistory> results = dispatchHistoryController.searchDispatchHistory(null, null);
//
//        // Assert
//        assertNotNull(results);
//    }
//
//
//

}


