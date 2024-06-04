package com.project.service;


import com.project.entities.Messages;
import com.project.entities.User;

import java.util.List;

public interface IMessageService {

    Messages sendMessage(Long userId,Long chatId,String message) throws Exception;

    List<Messages> getMessagesByProject(Long projectId) throws Exception;
}
