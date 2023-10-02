package com.learncode.authservice.service.impl;

import com.learncode.authservice.request.EmailMessage;
import com.learncode.authservice.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendMail(EmailMessage emailMessage) throws MessagingException {
        String to = emailMessage.getTo();
        String subject = emailMessage.getSubject();
        String mailBody = emailMessage.getMailBody();
        String cc = emailMessage.getCc();
        String bcc = emailMessage.getBcc();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setSubject(subject);
        if(Objects.nonNull(to) && !to.isEmpty()) {
            helper.setTo(to.split("\\;"));
        }
        if(Objects.nonNull(cc) && !cc.isEmpty()) {
            helper.setCc(cc.split("\\;"));
        }
        if(Objects.nonNull(bcc) && !bcc.isEmpty()) {
            helper.setBcc(bcc.split("\\;"));
        }
        helper.setText(mailBody, true); // true indicates body is HTML
        javaMailSender.send(message);
    }
}
