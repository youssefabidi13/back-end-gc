package com.gi.gestioncompetence.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceResignationImpl implements EmailServiceResignation {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceResignationImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception appropriately (log it or throw a custom exception)
        }
    }

    @Override
    public void sendResignationResponseEmail(String to, String subject, String body) {
        sendEmail(to, subject, body);
    }
}

