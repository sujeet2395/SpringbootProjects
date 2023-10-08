package com.learncode.notificationservice.service.interfaces;

import com.learncode.notificationservice.entity.Notification;
import com.learncode.notificationservice.request.EmailMessage;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);

    Notification updateNotification(Long notificationId, Notification notification);

    List<Notification> getAllNotificationByUsername(String username);

    void sendMail(EmailMessage emailMessage);
}
