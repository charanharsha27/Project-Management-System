package com.project.dao;

import com.project.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChatDao extends JpaRepository<Chat,Long> {
}
