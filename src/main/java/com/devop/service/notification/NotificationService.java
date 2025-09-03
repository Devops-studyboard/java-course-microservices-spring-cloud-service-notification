package com.devop.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(NotificationRequestDTO notificationRequest) {
        if (notificationRequest == null) {
            throw new IllegalArgumentException("Notification request cannot be null");
        }
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationRequest.email());
        message.setSubject(notificationRequest.subject());
        message.setText(notificationRequest.message());
        
        mailSender.send(message);
    }
}