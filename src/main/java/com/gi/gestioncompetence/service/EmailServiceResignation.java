package com.gi.gestioncompetence.service;

public interface EmailServiceResignation {
    void sendEmail(String to, String subject, String body);

    void sendResignationResponseEmail(String to, String subject, String body);
}
