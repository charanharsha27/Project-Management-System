package com.project.service;

import com.project.dao.IChatDao;
import com.project.entities.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IChatServiceImpl implements IChatService{

    @Autowired
    private IChatDao chatDao;

    @Override
    public Chat createChat(Chat chat) {
        return chatDao.save(chat);
    }
}
