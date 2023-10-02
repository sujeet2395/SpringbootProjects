package com.learncode.authservice.service.interfaces;

import com.learncode.authservice.request.EmailMessage;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendMail(EmailMessage emailMessage) throws MessagingException;
}
