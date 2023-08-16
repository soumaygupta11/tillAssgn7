package com.example.newsUser.contract.sourceWrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;

    public Source(Source source){
        this.id = source.getId();
        this.name = source.getName();
        this.description = source.getDescription();
        this.url = source.getUrl();
        this.category = source.getCategory();
        this.language = source.getLanguage();
        this.country = source.getCountry();
    }
    public Source(String id, String name, String description, String url, String category, String language, String country) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public Source() {
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getUrl() {
        return url;
    }
    public String getCategory() {
        return category;
    }
    public String getLanguage() {
        return language;
    }
    public String getCountry() {
        return country;
    }

}
