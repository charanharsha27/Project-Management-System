package com.project.service;

import com.project.dao.IssueDao;
import com.project.entities.Issues;
import com.project.entities.Project;
import com.project.entities.User;
import com.project.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class IssueServiceImpl implements IssueService{


    @Autowired
    private IssueDao issueDao;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @Override
    public Issues getIssueById(Long id) throws Exception {
        Optional<Issues> issue = issueDao.findById(id);

        if(issue.isPresent()){
            return issue.get();
        }

        throw new Exception("Issue not found with the id : "+id);
    }

    @Override
    public List<Issues> getIssueByProjectId(Long id) throws Exception {
         return issueDao.findByProjectID(id);

    }

    @Override
    public Issues createIssue(IssueRequest issue, User user) throws Exception {

        Issues issues = new Issues();
        issues.setTitle(issue.getTitle());
        issues.setDescription(issue.getDescription());
        issues.setStatus(issue.getStatus());
        issues.setProjectID(issue.getProjectID());
        issues.setPriority(issue.getPriority());
        issues.setDueDate(issue.getDueDate());
        issues.setProject(projectService.getProjectById(issue.getProjectID()));
//        issues.setUser(user);

        Project project = projectService.getProjectById(issue.getProjectID());
        issues.setProject(project);

        return issueDao.save(issues);
    }

    @Override
    public Optional<Issues> updateIssue(Long issueId, IssueRequest updatedIssue, Long userid) throws Exception {
        return Optional.empty();
    }

    @Override
    public void deleteIssue(Long issueId, Long userid) throws Exception {
        issueDao.deleteById(issueId);
    }

    @Override
    public List<Issues> getIssuesByAssigneeId(Long assigneeId) throws Exception {
        return List.of();
    }

    @Override
    public List<Issues> searchIssues(String title, String status, String priority, Long assigneeId) throws Exception {
        return List.of();
    }

    @Override
    public List<User> getAssigneeForIssue(Long issueId) throws Exception {
        return List.of();
    }

    @Override
    public Issues addUserToIssue(Long issueId, Long userid) throws Exception {
        User user = userService.findUserById(userid);
        Issues issue = getIssueById(issueId);

        issue.setUser(user);
        return issueDao.save(issue);
    }

    @Override
    public Issues updateStatus(Long issueId, String status) throws Exception {

        Issues issue = getIssueById(issueId);
        issue.setStatus(status);

        return issueDao.save(issue);
    }
}
