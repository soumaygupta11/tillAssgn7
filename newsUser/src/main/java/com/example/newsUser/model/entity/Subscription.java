package com.example.newsUser.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    private Long user_id;
    private boolean is_subscribed;

    public Subscription() {
    }

    public Subscription(Long user_id, boolean is_subscribed) {
        this.user_id = user_id;
        this.is_subscribed = is_subscribed;
    }

    public Long getUser_id() {
        return user_id;
    }

    public boolean getIs_subscribed() {
        return is_subscribed;
    }

    public void setIs_subscribed(boolean is_subscribed) {
        this.is_subscribed = is_subscribed;
    }

}
