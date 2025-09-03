package com.devop.service.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    private NotificationRequestDTO notificationRequest;

    @BeforeEach
    void setUp() {
        notificationRequest = new NotificationRequestDTO("test@example.com", "Test Subject", "Test message");
    }

    @Test
    void shouldSendNotificationSuccessfully() throws Exception {
        // Given
        doNothing().when(notificationService).sendNotification(any(NotificationRequestDTO.class));

        // When & Then
        mockMvc.perform(post("/api/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully"));

        verify(notificationService, times(1)).sendNotification(any(NotificationRequestDTO.class));
    }

    @Test
    void shouldHandleNotificationException() throws Exception {
        // Given
        doThrow(new RuntimeException("Email service unavailable"))
                .when(notificationService).sendNotification(any(NotificationRequestDTO.class));

        // When & Then
        mockMvc.perform(post("/api/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificationRequest)))
                .andExpect(status().isInternalServerError());

        verify(notificationService, times(1)).sendNotification(any(NotificationRequestDTO.class));
    }

    @Test
    void shouldReturnBadRequestForInvalidData() throws Exception {
        // Given
        NotificationRequestDTO invalidRequest = new NotificationRequestDTO("", "", "");

        // When & Then
        mockMvc.perform(post("/api/notifications/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(notificationService, never()).sendNotification(any(NotificationRequestDTO.class));
    }
}