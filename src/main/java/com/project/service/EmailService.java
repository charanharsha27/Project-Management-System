package com.project.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    public void sendEmail(String to,String link) throws MessagingException;
}
