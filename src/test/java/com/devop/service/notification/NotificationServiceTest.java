package com.devop.service.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationService notificationService;

    private NotificationRequestDTO notificationRequest;

    @BeforeEach
    void setUp() {
        notificationRequest = new NotificationRequestDTO("test@example.com", "Test Subject", "Test message");
    }

    @Test
    void shouldSendNotificationSuccessfully() {
        // Given
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // When & Then
        assertDoesNotThrow(() -> notificationService.sendNotification(notificationRequest));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailServiceFails() {
        // Given
        doThrow(new RuntimeException("Email service unavailable"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> notificationService.sendNotification(notificationRequest));
        
        assertEquals("Email service unavailable", exception.getMessage());
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void shouldHandleNullNotificationRequest() {
        // When & Then
        assertThrows(NullPointerException.class, 
                () -> notificationService.sendNotification(null));
        
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }
}