package com.example.newsUser.model.repository;

import com.example.newsUser.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SubscriptionRespository extends JpaRepository<Subscription, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE subscriptions" + " SET is_subscribed = 'true'" + " WHERE user_id= :user_id ", nativeQuery = true)
    void subscribe(Long user_id);


    @Transactional
    @Modifying
    @Query(value = "UPDATE subscriptions" + " SET is_subscribed = 'false'" + " WHERE user_id= :user_id ", nativeQuery = true)
    void unSubscribe(Long user_id);

    @Query(value = "SELECT user_id FROM subscriptions WHERE is_subscribed = 'true'", nativeQuery = true)
    List<Long> getSubscribedUsers();
}
