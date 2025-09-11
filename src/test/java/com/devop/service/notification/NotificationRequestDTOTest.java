package com.devop.service.notification;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationRequestDTOTest {

    @Test
    void shouldCreateNotificationRequestDTO() {
        // Given
        String email = "test@example.com";
        String subject = "Test Subject";
        String message = "Test Message";
        
        // When
        NotificationRequestDTO dto = new NotificationRequestDTO(email, subject, message);
        
        // Then
        assertEquals(email, dto.email());
        assertEquals(subject, dto.subject());
        assertEquals(message, dto.message());
    }

    @Test
    void shouldHandleNullValues() {
        // When
        NotificationRequestDTO dto = new NotificationRequestDTO(null, null, null);
        
        // Then
        assertNull(dto.email());
        assertNull(dto.subject());
        assertNull(dto.message());
    }

    @Test
    void shouldHandleEmptyStrings() {
        // Given
        String email = "";
        String subject = "";
        String message = "";
        
        // When
        NotificationRequestDTO dto = new NotificationRequestDTO(email, subject, message);
        
        // Then
        assertEquals("", dto.email());
        assertEquals("", dto.subject());
        assertEquals("", dto.message());
    }
}