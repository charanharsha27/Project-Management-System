package com.project.service;

import com.project.dao.InvitationDao;
import com.project.entities.Invitation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvitationDao invitationDao;

    @Override
    public void sendInvitation(String email, Long ProjectId) throws MessagingException {


        String token = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(ProjectId);
        invitation.setToken(token);

        String link = "http://localhost:5173/acceptInvite?token="+token;
        emailService.sendEmail(email,link);
    }

    @Override
    public Invitation acceptInvitation(String token, Long id) throws Exception {

        Invitation invitation = invitationDao.findByToken(token);

        if(invitation==null){
            throw new Exception("Invitation invalid");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String email) {

        Invitation invitation = invitationDao.findByEmail(email);
        return invitation.getEmail();
    }

    @Override
    public void deleteToken(String token) throws Exception {
        Invitation invitation = invitationDao.findByToken(token);
        if(invitation!=null){
            invitationDao.delete(invitation);
        }else{
            throw new Exception("Invitation not found");
        }

    }
}
