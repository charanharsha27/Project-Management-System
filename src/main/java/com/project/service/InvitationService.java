package com.project.service;

import com.project.entities.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email, Long ProjectId) throws MessagingException;

    public Invitation acceptInvitation(String token, Long id) throws Exception;

    public String getTokenByUserMail(String email);

    public void deleteToken(String token) throws Exception;

}
