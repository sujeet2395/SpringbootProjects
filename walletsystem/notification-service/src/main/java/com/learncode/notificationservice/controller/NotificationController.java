package com.learncode.notificationservice.controller;

import com.learncode.notificationservice.entity.Notification;
import com.learncode.notificationservice.request.EmailMessage;
import com.learncode.notificationservice.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService; // Inject the NotificationService

    // Create a new notification
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createNotification(@RequestBody Notification notification) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", notificationService.createNotification(notification));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing notification by its ID
    @PutMapping("/{notificationId}")
    public ResponseEntity<Map<String, Object>> updateNotification(@PathVariable Long notificationId, @RequestBody Notification notification) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", notificationService.updateNotification(notificationId, notification));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a notification by its ID
    @GetMapping("/by-username/{username}")
    public ResponseEntity<Map<String, Object>> getAllNotificationByUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", notificationService.getAllNotificationByUsername(username));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/send-mail")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody EmailMessage emailMessage) {
        Map<String, Object> response = new HashMap<>();
        try {
            notificationService.sendMail(emailMessage);
            response.put("data","Email sent successfully.");
            response.put("success",true);
        } catch (Exception ex) {
            response.put("success",false);
            response.put("exception", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
