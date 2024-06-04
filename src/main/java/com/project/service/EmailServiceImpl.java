package com.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendEmail(String to, String link) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");

        String subject = "Invitation Link For Joining Project Team";
        String text = "Click the below link to get started\n\n"+link;

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        try{
            mailSender.send(mimeMessage);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
