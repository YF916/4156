package com.example.demotest;


import com.example.demotest.controller.DispatchController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import com.example.demotest.controller.DispatchHistoryController;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;


@SpringBootTest
public class DispatchControllerTest {

    @InjectMocks
    private DispatchController dispatchController;

    @Mock
    private ResponderRepository responderRepository;

    @Captor
    private ArgumentCaptor<Responder> responderCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewResponder_Success() {
        Responder responder = new Responder();
        responder.setName("John Smith");
        responder.setPhone("1234567890");
        responder.setLatitude(40.7128);
        responder.setLongitude(-74.0060);
        responder.setStatus("available");

        when(responderRepository.save(any(Responder.class))).thenReturn(responder);

        dispatchController.addNewResponder(responder);

        // Capture the responder passed to the save method
        verify(responderRepository).save(responderCaptor.capture());
        Responder savedResponder = responderCaptor.getValue();

        // Assert that the properties match
        assertEquals("John Smith", savedResponder.getName());
        assertEquals("1234567890", savedResponder.getPhone());
        assertEquals(40.7128, savedResponder.getLatitude());
        assertEquals(-74.0060, savedResponder.getLongitude());
        assertEquals("available", savedResponder.getStatus());
    }

    @Test
    public void testAddNewResponder_Failure() {
        Responder responder = new Responder();
        responder.setName("Jane Doe");
        responder.setPhone("9876543210");

        when(responderRepository.save(responder)).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> dispatchController.addNewResponder(responder));
        verify(responderRepository, times(1)).save(responder);
    }

    @Test
    public void testGetAllResponders() {
        /*Responder responder1 = new Responder();
        responder1.setName("John Smith");
        Responder responder2 = new Responder();
        responder2.setName("Jane Doe");

        List<Responder> mockResponders = Arrays.asList(responder1, responder2);

        when(responderRepository.findAll()).thenReturn(mockResponders);

        Iterable<Responder> responders = dispatchController.getAllResponders();
        assertNotNull(responders);
        assertEquals(2, ((List<Responder>) responders).size());
        verify(responderRepository, times(1)).findAll();*/
    }

}

