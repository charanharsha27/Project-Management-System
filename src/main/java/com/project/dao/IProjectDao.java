package com.project.dao;

import com.project.entities.Project;
import com.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProjectDao extends JpaRepository<Project, Long> {

//    List<Project> findByOwner(User user);

    List<Project> findByProjectNameContainingAndTeamContains(String projectName, User user);

//    @Query("select p from Project p join p.team t where t=:user")
//    List<Project> findByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user,User owner);
}
