package com.example.newsUser.model.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Column;

public interface EndpointCount {
    String getEndpoint();
    Long getCount();



}

