package com.project.dao;

import com.project.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubscriptionDao extends JpaRepository<Subscription,Long> {

    Subscription findByUserId(long userId);
}
