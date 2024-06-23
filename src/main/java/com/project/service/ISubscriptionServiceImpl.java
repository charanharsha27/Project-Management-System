package com.project.service;


import com.project.dao.ISubscriptionDao;
import com.project.entities.Subscription;
import com.project.entities.User;
import com.project.helper.PlanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ISubscriptionServiceImpl implements ISubscriptionService {

    @Autowired
    private ISubscriptionDao subscriptionDao;

    @Autowired
    private IUserService userService;


    @Override
    public Subscription createSubscription(User user) {

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionDao.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Long userId, PlanType planType) {

        Subscription subscription = subscriptionDao.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());

        if(planType.equals(PlanType.ANNUALLY)) {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }else{
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }
        System.out.println("updating subscription "+subscription);
        return subscriptionDao.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) {
        Subscription subscription = subscriptionDao.findByUserId(userId);

        if(!isValidSubscription(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionStartDate(LocalDate.now());
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }

        return subscriptionDao.save(subscription);

    }

    @Override
    public boolean isValidSubscription(Subscription subscription) {

        if(subscription.getPlanType().equals(PlanType.FREE)) {
            return true;
        }
        LocalDate subscriptionStartDate = subscription.getSubscriptionStartDate();
        LocalDate subscriptionEndDate = subscription.getSubscriptionEndDate();
        return !subscriptionStartDate.isAfter(subscriptionEndDate);
    }
}
