package com.example.newsUser.contract;

public class CreateUserRequest {
    private String email;
    private String selected_country;
    private String selected_category;

    public String getSelected_country() {
        return selected_country;
    }

    public String getSelected_category() {
        return selected_category;
    }

    public CreateUserRequest(String email, String selectec_country, String selected_language, String selected_category) {
        this.email = email;
        this.selected_country = selectec_country;
        this.selected_category = selected_category;
    }

    public CreateUserRequest() {
    }

    public String getEmail() {
        return email;
    }


}
