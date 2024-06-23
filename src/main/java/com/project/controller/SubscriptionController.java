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
    private ResponseEntity<Subscription> getSubscription(@RequestHeader("Authorization") String token) {

        User user = userService.findUserByJwt(token);

        System.out.println("in get Subscription");

        Subscription subscription = subscriptionService.getUserSubscription(user.getId());
        System.out.println(subscription);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/upgrade-subscription")
    private ResponseEntity<Subscription> upgradeSubscription(@RequestHeader("Authorization") String jwt,
                                                            @RequestParam PlanType planType){

        System.out.println(planType);
        User user = userService.findUserByJwt(jwt);

        Subscription subscription = subscriptionService.updateSubscription(user.getId(), planType);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
