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
        int rating = 5;
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

}

