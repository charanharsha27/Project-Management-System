package com.project.service;


import com.project.entities.Chat;
import com.project.entities.Project;
import com.project.entities.User;

import java.util.List;

public interface IProjectService {

    Project createProject(Project project,User user);

    List<Project> getProjectsByTeam(User user,String category,String tag);

    Project getProjectById(Long id);

    void deleteProject(Long projectId,Long userId);

    Project UpdateProject(Long projectId,Project updatedProject);

    void addUserToProject(Long ProjectId,Long userId);

    void deleteUserFromProject(Long ProjectId,Long userId);

    Chat getChatByProjectId(Long ProjectId);

    List<Project> getProjectsByName(String containing,User user);
}
