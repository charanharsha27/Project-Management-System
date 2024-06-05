package com.project.service;

import com.project.dao.ICommentDao;
import com.project.dao.IUserDao;
import com.project.dao.IssueDao;
import com.project.entities.Comment;
import com.project.entities.Issues;
import com.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ICommentServiceImpl implements ICommentService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private ICommentDao commentDao;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public Comment createComment(Long issueId, Long userId, String comment) throws Exception {

        User user = userService.findUserById(userId);
        Issues issue = issueService.getIssueById(issueId);

        Comment com = new Comment();
        com.setCreatedDateTime(LocalDateTime.now());
        com.setContent(comment);
        com.setUser(user);
        com.setIssue(issue);

        issue.getComments().add(com);
        issueDao.save(issue); //****//

        return commentDao.save(com);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Comment comment = commentDao.findByIdAndUser(commentId,user);
        commentDao.delete(comment);

//        Optional<Comment> comment = commentDao.findById(commentId);
//        Optional<User> user = userDao.findById(userId);
//
//        if(comment.isEmpty()){
//            throw new Exception("Comment not found");
//        }
//
//        if(user.isEmpty()){
//            throw new Exception("User not found");
//        }
//
//        if(comment.get().getUser().equals(user)){
//            commentDao.delete(comment.get());
//        }
//        else{
//            throw new Exception("Comment not deleted by you");
//        }
    }

    @Override
    public List<Comment> getCommentsByIssueId(Long issueId) throws Exception {
        Optional<Issues> issue = issueDao.findById(issueId);

        if(issue.isPresent()){
            return issue.get().getComments();
        }

        throw new Exception("No comments found with the provided id");
    }
}
