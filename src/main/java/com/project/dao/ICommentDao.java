package com.project.dao;

import com.project.entities.Comment;
import com.project.entities.Issues;
import com.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentDao extends JpaRepository<Comment,Long> {

    Comment findByIdAndUser(Long id, User user);

    List<Comment> findByIssue(Issues issue);
}
