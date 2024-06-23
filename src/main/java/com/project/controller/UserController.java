package com.project.controller;

import com.project.entities.User;
import com.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("get-profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token){
        System.out.println(token);
        User user = userService.findUserByJwt(token);
        System.out.println("user fetched "+user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
