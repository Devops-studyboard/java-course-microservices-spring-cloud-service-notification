package com.devop.service.notification;


public record NotificationRequestDTO(String email, String subject, String message) {
}
