package com.project.service;

import com.project.entities.Issues;
import com.project.entities.User;
import com.project.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issues getIssueById(Long id) throws Exception;

    List<Issues> getIssueByProjectId(Long id) throws Exception;

    Issues createIssue(IssueRequest issue,User user) throws Exception;

    Optional<Issues> updateIssue(Long issueId, IssueRequest updatedIssue, Long userid) throws Exception;

    void deleteIssue(Long issueId, Long userid) throws Exception;

    List<Issues> getIssuesByAssigneeId(Long assigneeId) throws Exception;

    List<Issues> searchIssues(String title,String status,String priority,Long assigneeId) throws Exception;

    List<User> getAssigneeForIssue(Long issueId) throws Exception;

    Issues addUserToIssue(Long issueId,Long userid) throws Exception;

    Issues updateStatus(Long issueId,String status) throws Exception;

}
