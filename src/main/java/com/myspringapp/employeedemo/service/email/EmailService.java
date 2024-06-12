package com.myspringapp.employeedemo.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom("pranav.bhide818@gmail.com");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


}
