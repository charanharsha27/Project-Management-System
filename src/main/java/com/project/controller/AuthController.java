package com.project.controller;

import com.project.configuration.JwtProvider;
import com.project.dao.IUserDao;
import com.project.entities.Subscription;
import com.project.entities.User;
import com.project.helper.ResourceNotFoundException;
import com.project.request.LoginRequest;
import com.project.response.AuthenticationResponse;
import com.project.service.ISubscriptionService;
import com.project.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetails;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private ISubscriptionService subscriptionService;


    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody User user) {

        User isExist = userDao.findByEmail(user.getEmail());
        if (isExist != null) {
            throw new ResourceNotFoundException("User with this email already exists");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());

        User savedUser = userDao.save(newUser);

        Subscription subscription = subscriptionService.createSubscription(savedUser); // creating a free subscription plan on user creation.

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtProvider.generateToken(authentication);

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setToken(jwtToken);
        authResponse.setMsg("User registered successfully");


        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);



    }

    @PostMapping("/login")
    private ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = Authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwtToken = JwtProvider.generateToken(authentication);
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setToken(jwtToken);
        authResponse.setMsg("User login successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication Authenticate(String username, String password) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
