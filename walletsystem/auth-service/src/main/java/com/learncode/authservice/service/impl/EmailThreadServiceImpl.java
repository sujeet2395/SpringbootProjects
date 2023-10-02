package com.learncode.authservice.service.impl;

import com.learncode.authservice.request.EmailMessage;
import com.learncode.authservice.service.interfaces.EmailService;
import jakarta.mail.MessagingException;

public class EmailThreadServiceImpl extends Thread{

    private final EmailMessage emailMessage;

    private final EmailService emailService;

    public EmailThreadServiceImpl(EmailService emailService, EmailMessage emailMessage)
    {
        this.emailService = emailService;
        this.emailMessage = emailMessage;
    }

    @Override
    public void run() {
        try {
            emailService.sendMail(emailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
