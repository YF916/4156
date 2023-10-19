package com.example.demotest;

import com.example.demotest.controller.DispatchHistoryController;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DispatchHistoryControllerTest {

    @InjectMocks
    private DispatchHistoryController dispatchHistoryController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponderRepository responderRepository;

    @Mock
    private DispatchHistoryRepository dispatchHistoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartNewDispatch_NonExistentUserOrResponder() {
        int userId = 999; // non-existent user
        int responderId = 999; // non-existent responder
        LocalDateTime startTime = LocalDateTime.now();

        when(userRepository.getReferenceById(userId)).thenReturn(null);
        when(responderRepository.getReferenceById(responderId)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dispatchHistoryController.addNewDispatchHistory(userId, responderId, startTime);
        });

        assertNotNull(exception);
        verify(dispatchHistoryRepository, times(0)).save(any(DispatchHistory.class));
    }


    @Test
    public void testStartNewDispatch_Success() {
        int userId = 1;
        int responderId = 1;
        LocalDateTime startTime = LocalDateTime.now();

        when(userRepository.getReferenceById(userId)).thenReturn(new User());
        when(responderRepository.getReferenceById(responderId)).thenReturn(new Responder());

        String response = dispatchHistoryController.addNewDispatchHistory(userId, responderId, startTime);
        assertEquals("Saved", response);
        verify(dispatchHistoryRepository, times(1)).save(any(DispatchHistory.class));
    }

    @Test
    public void testRateDispatch_Success() {
        int dispatchId = 1;
        Double rating = 5.0;
        String feedback = "Great service!";
        DispatchHistory mockDispatch = new DispatchHistory();

        when(dispatchHistoryRepository.getReferenceById(dispatchId)).thenReturn(mockDispatch);

        String response = dispatchHistoryController.rateDispatchHistory(dispatchId, rating, feedback);
        assertEquals("Rated", response);
        verify(dispatchHistoryRepository, times(1)).save(mockDispatch);
    }

    @Test
    public void testUpdateArrivalTime_Success() {
        int dispatchId = 1;
        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(1);
        DispatchHistory mockDispatch = new DispatchHistory();

        when(dispatchHistoryRepository.getReferenceById(dispatchId)).thenReturn(mockDispatch);

        String response = dispatchHistoryController.updateArrivalTime(dispatchId, arrivalTime);
        assertEquals("Rated", response);
        verify(dispatchHistoryRepository, times(1)).save(mockDispatch);
    }

    @Test
    public void testAddNewDispatchHistory_validInput_savesDispatchHistory() {
        // Arrange
        DispatchHistory dispatchHistory = new DispatchHistory();
        when(userRepository.getReferenceById(anyInt())).thenReturn(new User());
        when(responderRepository.getReferenceById(anyInt())).thenReturn(new Responder());

        // Act
        dispatchHistoryController.addNewDispatchHistory(1, 2, LocalDateTime.now());

        // Assert
        verify(dispatchHistoryRepository, times(1)).save(any(DispatchHistory.class));
    }

    @Test
    public void testRateDispatchHistory_validInput_ratesDispatchHistory() {
        // Arrange
        DispatchHistory dispatchHistory = new DispatchHistory();
        when(dispatchHistoryRepository.getReferenceById(anyInt())).thenReturn(dispatchHistory);

        // Act
        dispatchHistoryController.rateDispatchHistory(1, 5.0, "Good service");

        // Assert
        assertEquals(5, dispatchHistory.getRating());
        assertEquals("Good service", dispatchHistory.getFeedback());
    }



    @Test
    public void testUpdateArrivalTime_validInput_updatesArrivalTime() {
        // Arrange
        DispatchHistory dispatchHistory = new DispatchHistory();
        LocalDateTime arrivalTime = LocalDateTime.now();
        when(dispatchHistoryRepository.getReferenceById(anyInt())).thenReturn(dispatchHistory);

        // Act
        dispatchHistoryController.updateArrivalTime(1, arrivalTime);

        // Assert
        assertEquals(arrivalTime, dispatchHistory.getArrivalTime());
    }




    @Test
    public void testSearchDispatchHistory_noFilter_returnsAll() {
        // Arrange
        when(dispatchHistoryRepository.findAll()).thenReturn(new ArrayList<DispatchHistory>());

        // Act
        Iterable<DispatchHistory> results = dispatchHistoryController.searchDispatchHistory(null, null);

        // Assert
        assertNotNull(results);
    }

    @Test
    public void testSearchDispatchHistory_validInput_returnsDispatchHistories() {
        // Arrange
        when(dispatchHistoryRepository.findAll()).thenReturn(new ArrayList<DispatchHistory>());

        // Act
        Iterable<DispatchHistory> results = dispatchHistoryController.searchDispatchHistory(null, null);

        // Assert
        assertNotNull(results);
    }




}


