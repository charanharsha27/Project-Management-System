package com.project.controller;


import com.project.dao.IProjectDao;
import com.project.dao.InvitationDao;
import com.project.entities.Chat;
import com.project.entities.Invitation;
import com.project.entities.Project;
import com.project.entities.User;
import com.project.request.InvitationRequest;
import com.project.service.IProjectService;
import com.project.service.IUserService;
import com.project.service.InvitationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping("/getProjects")
    private ResponseEntity<List<Project>> getProjects(@RequestHeader("Authentication") String token,
                                                      @RequestParam(required = false) String category,
                                                      @RequestParam(required = false) String tag)
    {
        User user = userService.findUserByJwt(token);

        List<Project> projects = projectService.getProjectsByTeam(user,category,tag);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/create-project")
    private ResponseEntity<Project> getProjectById(@RequestHeader("Authentication") String token,
    @RequestBody Project p)
    {
        User user = userService.findUserByJwt(token);

        Project project = projectService.createProject(p,user);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/update-project/{projectId}")
    private ResponseEntity<Project> updateProject(@RequestHeader("Authentication") String token,
                                                   @RequestBody Project p,@PathVariable Long projectId)
    {
        User user = userService.findUserByJwt(token);

        Project project = projectService.UpdateProject(projectId,p);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @DeleteMapping("/delete-project/{projectId}")
    private ResponseEntity<String> DeleteProject(@RequestHeader("Authentication") String token,
                                                  @RequestBody Project p,@PathVariable Long projectId)
    {
        User user = userService.findUserByJwt(token);

        projectService.deleteProject(projectId,user.getId());

        return new ResponseEntity<>("Project deleted", HttpStatus.OK);
    }

    @PostMapping("/getchat/{projectId}")
    private ResponseEntity<Chat> GetChat(@RequestHeader("Authentication") String token,
                                                 @PathVariable Long projectId)
    {

        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/send-invite")
    private ResponseEntity<String> sendInvite(@RequestBody InvitationRequest request,
                                               @RequestHeader("Authentication") String token,
                                               @RequestBody Project project) throws MessagingException {
        User user = userService.findUserByJwt(token);

        invitationService.sendInvitation(request.getEmail(), request.getProjectId());

        return new ResponseEntity<>("Invitation sent", HttpStatus.OK);
    }

    @GetMapping("/accept-invite")
    private ResponseEntity<Invitation> acceptInvite(@RequestBody InvitationRequest request,
                                              @RequestHeader("Authentication") String jwt,
                                              @RequestParam String token,
                                              @RequestBody Project project) throws Exception {
        User user = userService.findUserByJwt(jwt);

        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(project.getId(), user.getId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }
}
