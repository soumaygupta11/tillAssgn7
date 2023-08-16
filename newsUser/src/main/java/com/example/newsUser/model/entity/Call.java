package com.example.newsUser.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String endpoint;
    private String request;
    private String response;
    private String timetaken;

    private Long user_id;

    private String created_at;

    public String getEndpoint() {
        return endpoint;
    }

    public String getRequest() {
        return request;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setTimetaken(String timetaken) {
        this.timetaken = timetaken;
    }

    public String getResponse() {
        return response;
    }

    public String getTimetaken() {
        return timetaken;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    private Call() {
    }
    public Call(String endpoint, String request, String response, String timetaken, Long user_id, String created_at) {
        this.endpoint = endpoint;
        this.request = request;
        this.response = response;
        this.timetaken = timetaken;
        this.created_at = created_at;
        this.user_id = user_id;
    }


}
