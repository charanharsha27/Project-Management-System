package com.project.service;

import com.project.dao.IChatDao;
import com.project.dao.IProjectDao;
import com.project.dao.IUserDao;
import com.project.entities.Chat;
import com.project.entities.Project;
import com.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectDao projectDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserService userService;

    @Autowired
    private IChatDao chatDao;

    @Override
    public Project createProject(Project project, User user) {
        project.setOwner(user);
        project.getTeam().add(user);
        userService.updateUserProjectSize(user, 1);
        Chat chat = new Chat();
        chat.setProject(project);
        project.setChat(chat);
        return projectDao.save(project);
    }

    @Override
    public List<Project> getProjectsByTeam(User user, String category, String tag) {
        List<Project> projects = projectDao.findByTeamContainingOrOwner(user,user); // get projects either by selecting from list of users or select the projects that the current user has

        if(category!=null){
            projects = projects.stream().filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList()); //getCategory()
        }

        if(tag!=null){
            projects = projects.stream().filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectDao.findById(id).orElseThrow(()-> new RuntimeException("Project not found"));
    }

    @Override
    public void deleteProject(Long projectId, Long userId) {
        User user = userService.findUserById(userId);
        user.setProjectsSize(user.getProjectsSize()-1);
        projectDao.deleteById(projectId);
    }

    @Override
    public Project UpdateProject(Long projectId, Project updatedProject) {
        Project project = getProjectById(projectId);
        if(project!=null){
            return projectDao.save(updatedProject);
        }

        return null;
    }

    @Override
    public void addUserToProject(Long ProjectId, Long userId) {

//        User user = userDao.findById(userId).orElseThrow( () -> new RuntimeException("User not found"));
//
//        if(user!=null){
//            Project project = getProjectById(ProjectId);
//            if(!project.getTeam().contains(user)){
//                project.getTeam().add(user);
//                project.getChat().getUsers().add(user);
//                projectDao.save(project);
//            }
//
//        }

        Project project = getProjectById(ProjectId);
        User user = userDao.findUserById(userId);

        for(User member : project.getTeam()){
            if(member.getId().equals(userId)){
                return;
            }
        }

        project.getChat().getUsers().add(user);
        project.getTeam().add(user);
        user.getProjects().add(project);
//        user.
        projectDao.save(project);

    }

    @Override
    public void deleteUserFromProject(Long ProjectId, Long userId) {

        User user = userDao.findById(userId).orElseThrow( () -> new RuntimeException("User not found"));

        if(user!=null){
            Project project = getProjectById(ProjectId);
            if(project.getTeam().contains(user)){
                project.getTeam().remove(user);
                project.getChat().getUsers().remove(user);
            }
        }

    }

    @Override
    public Chat getChatByProjectId(Long ProjectId) {
        Project project = getProjectById(ProjectId);
        System.out.println(project.getChat());
        return project.getChat();
    }

    @Override
    public List<Project> getProjectsByName(String containing, User user) {
        String contains = "%"+containing+"%";

        return projectDao.findByProjectNameContainingAndTeamContains(containing,user);
    }
}
