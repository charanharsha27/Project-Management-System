package com.project.service;

import com.project.dao.IMessageDao;
import com.project.entities.Chat;
import com.project.entities.Messages;
import com.project.entities.Project;
import com.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IMessageServiceImpl implements IMessageService {

    @Autowired
    private IMessageDao messageDao;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;

    @Override
    public Messages sendMessage(Long userId, Long projectId, String message) throws Exception {

        User user = userService.findUserById(userId);
        Chat chat = projectService.getChatByProjectId(projectId);

        Messages messages = new Messages();
        messages.setMessage(message);
        messages.setSender(user);
        messages.setChat(chat);
        messages.setCreatedAt(LocalDateTime.now());

        chat.getMessages().add(messages);
        return messageDao.save(messages);
    }

    @Override
    public List<Messages> getMessagesByProject(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageDao.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
