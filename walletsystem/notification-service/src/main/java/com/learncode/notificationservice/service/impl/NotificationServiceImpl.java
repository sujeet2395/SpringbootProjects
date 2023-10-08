package com.learncode.notificationservice.service.impl;

import com.learncode.notificationservice.apiconstant.Constant;
import com.learncode.notificationservice.entity.Notification;
import com.learncode.notificationservice.exception.ResourceNotFoundException;
import com.learncode.notificationservice.repository.NotificationRepository;
import com.learncode.notificationservice.request.EmailMessage;
import com.learncode.notificationservice.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        if(!(notification.getType().equals(Constant.NOTIFICATION_TRANSACTION_TYPE) || notification.getType().equals(Constant.NOTIFICATION_WALLET_TYPE) || notification.getType().equals(Constant.NOTIFICATION_ALL_TYPE)))
            throw new RuntimeException("Notification type is incorrect.");
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Long notificationId, Notification notification) {
        Notification notificationInstanceFromDB = notificationRepository.findById(notificationId).orElseThrow(() -> new ResourceNotFoundException("Notification is not found."));
        if(Objects.nonNull(notification.getUsername()) && !notification.getUsername().isEmpty())
            notificationInstanceFromDB.setUsername(notification.getUsername());
        if(Objects.nonNull(notification.getRead()))
            notificationInstanceFromDB.setRead(notification.getRead());
        return notificationRepository.save(notificationInstanceFromDB);
    }

    @Override
    public List<Notification> getAllNotificationByUsername(String username) {
        return notificationRepository.findByUsername(username);
    }

    @Override
    public void sendMail(EmailMessage emailMessage) {
        System.out.println(emailMessage);
    }
}
