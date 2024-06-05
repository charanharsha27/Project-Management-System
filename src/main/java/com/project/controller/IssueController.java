package com.project.controller;

import com.project.DTO.IssueDTO;
import com.project.dao.IssueDao;
import com.project.entities.Issues;
import com.project.entities.User;
import com.project.request.IssueRequest;
import com.project.service.IUserService;
import com.project.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private IUserService userService;


    @GetMapping("/{issueId}")
    public ResponseEntity<Issues> getIssueById(@PathVariable Long issueId) throws Exception{
        Issues issue = issueService.getIssueById(issueId);

        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issues>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
        List<Issues> issues = issueService.getIssueByProjectId(projectId);

        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @PostMapping("/create-issue")
    public ResponseEntity<Issues> createIssue(@RequestBody IssueRequest issue,@RequestHeader("Authorization") String token) throws Exception{
        User tokenUser = userService.findUserByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issues createdIssue = issueService.createIssue(issue,tokenUser);

        return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);

    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<String> deleteIssue(@PathVariable Long issueId,@RequestHeader("Authorization") String token) throws Exception{
        User user = userService.findUserByJwt(token);
        issueService.deleteIssue(issueId,user.getId());

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<String> addUserToIssue(@PathVariable Long issueId,@PathVariable Long userId,@RequestHeader("Authorization") String token) throws Exception{
        issueService.addUserToIssue(issueId,userId);

        return new ResponseEntity<>("Assigned", HttpStatus.OK);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issues> updateIssueStatus(@PathVariable Long issueId,@PathVariable String status,@RequestHeader("Authorization") String token) throws Exception{
        Issues issue = issueService.updateStatus(issueId,status);

        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

}
