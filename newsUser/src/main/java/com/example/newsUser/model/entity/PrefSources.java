package com.example.newsUser.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "pref_sources")
public class PrefSources {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long sid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String source;

    public PrefSources() {
    }

    public PrefSources(User user, String source) {
        this.user = user;
        this.source = source;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
