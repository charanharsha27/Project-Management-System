package com.project.controller;


import com.project.entities.Comment;
import com.project.entities.Issues;
import com.project.entities.User;
import com.project.request.CommentRequest;
import com.project.service.ICommentService;
import com.project.service.IUserService;
import com.project.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IssueService issueService;

    @PostMapping("/create-comment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest,
                                                 @RequestHeader("Authorization") String token) throws Exception{

        User user = userService.findUserByJwt(token);
        Issues issue  = issueService.getIssueById(commentRequest.getIssueId());
        Comment comment = commentService.createComment(issue.getId(),user.getId(), commentRequest.getComment());

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwt(jwt);
        commentService.deleteComment(commentId,user.getId());

        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);

    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable long issueId) throws Exception {

        return new ResponseEntity<>(commentService.getCommentsByIssueId(issueId),HttpStatus.OK);
    }
}
