package com.project.controller;


import com.project.entities.Chat;
import com.project.entities.Messages;
import com.project.entities.User;
import com.project.request.MessageRequest;
import com.project.service.IChatService;
import com.project.service.IMessageService;
import com.project.service.IProjectService;
import com.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;


    @PostMapping("/create-message")
    public ResponseEntity<Messages> createMessage(@RequestBody MessageRequest msg,
                                                  @RequestHeader("Authentication") String token) throws Exception {

        User user = userService.findUserByJwt(token);

        Chat chat = projectService.getProjectById(msg.getProjectId()).getChat();

        if(chat == null){
            throw new Exception("No chat found with the project");
        }

        Messages message = messageService.sendMessage(msg.getSenderId(),msg.getProjectId(), msg.getContent());

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Messages>> getChatMessages(@PathVariable("projectId") Long projectId) throws Exception {
        List<Messages> messagesList = messageService.getMessagesByProject(projectId);

        return new ResponseEntity<>(messagesList, HttpStatus.OK);
    }

}
