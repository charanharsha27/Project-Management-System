package com.project.dao;

import com.project.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationDao extends JpaRepository<Invitation, Long> {

    Invitation findByToken(String token);

    Invitation findByEmail(String email);
}
