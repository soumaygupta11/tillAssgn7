package com.example.newsUser.contract.newsArticleWrapper;

import com.example.newsUser.contract.sourceWrapper.Source;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceArticle {
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private String id;
    private String name;

    public SourceArticle(Source source){
        this.id = source.getId();
        this.name = source.getName();
    }
    public SourceArticle(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public SourceArticle() {
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
