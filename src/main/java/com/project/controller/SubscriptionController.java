package com.project.controller;


import com.project.entities.Subscription;
import com.project.entities.User;
import com.project.helper.PlanType;
import com.project.service.ISubscriptionService;
import com.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private ISubscriptionService subscriptionService;

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    private ResponseEntity<Subscription> getSubscription(@RequestHeader("Authentication") String token) {

        User user = userService.findUserByJwt(token);

        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PostMapping("/upgrade-subscription")
    private ResponseEntity<Subscription> upgradeSubscription(@RequestHeader("Authentication") String token,
                                                            @RequestParam PlanType plantype){

        User user = userService.findUserByJwt(token);

        Subscription subscription = subscriptionService.updateSubscription(user.getId(), plantype);

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
