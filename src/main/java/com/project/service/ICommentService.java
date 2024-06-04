package com.project.service;

import com.project.entities.Comment;
import com.project.entities.Issues;
import com.project.entities.User;

import java.util.List;

public interface ICommentService {

    Comment createComment(Long issueId, Long userId, String comment) throws Exception;

    void deleteComment(Long commentId,Long userId) throws Exception;

    List<Comment> getCommentsByIssueId(Long issueId) throws Exception;
}
