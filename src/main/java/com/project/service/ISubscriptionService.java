package com.project.service;

import com.project.entities.Subscription;
import com.project.entities.User;
import com.project.helper.PlanType;

public interface ISubscriptionService {

    Subscription createSubscription(User user);

    Subscription updateSubscription(Long userId, PlanType planType);

    Subscription getUserSubscription(Long userId);

    boolean isValidSubscription(Subscription subscription);
}
