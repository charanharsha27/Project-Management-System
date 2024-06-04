package com.project.dao;

import com.project.entities.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMessageDao extends JpaRepository<Messages,Long> {

    List<Messages> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
