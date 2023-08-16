package com.example.newsUser.contract;

public class CreateUserResponse {
    private Long id;

    public CreateUserResponse(Long id) {
        this.id = id;
    }

    public CreateUserResponse() {
    }

    public Long getId() {
        return id;
    }
}
