package com.example.newsUser.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
      flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    private String selected_country;
    private String selected_category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<PrefSources> prefSources;

    private User() {
    }


    public User(String email, String selected_country, String selected_category, List<PrefSources> prefSources) {
        this.email = email;
        this.selected_country = selected_country;
        this.selected_category = selected_category;
        this.prefSources = prefSources;
    }

    public Long getUniqueID() {
        return id;
    }

    public String getSelected_category() {
        return selected_category;
    }

    public String getEmail() {
        return email;
    }

    public String getSelected_country() {
        return selected_country;
    }





}
