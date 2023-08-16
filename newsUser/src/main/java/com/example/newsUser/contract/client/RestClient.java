package com.example.newsUser.contract.client;

import com.example.newsUser.contract.newsArticleWrapper.Article;
import com.example.newsUser.contract.newsArticleWrapper.newsArticle;
import com.example.newsUser.contract.sourceWrapper.ListSource;
import com.example.newsUser.model.entity.Call;
import com.example.newsUser.model.repository.CRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class RestClient {
    @Autowired
    CRepository cRepository;
    RestTemplate template = new RestTemplate();

    public newsArticle getArticleResponse(String endpoint, String request, Long uid) throws JsonProcessingException {

        String timetaken = "";
        String responseString = cRepository.getResponse(request);
        newsArticle response = new newsArticle();

        if (responseString == null || responseString.isEmpty()){

            Long startTime = System.nanoTime();

            response = template.getForObject(
                    request,  // url to the api
                    newsArticle.class  // the expected response type is Article[]
            );

            Long endTime = System.nanoTime();
            timetaken = String.valueOf((endTime - startTime));
            responseString = String.valueOf(response);


        }
        else{
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(responseString, newsArticle.class);

            timetaken = cRepository.getTimeTaken(request);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        Call call  = new Call(endpoint, request, responseString, timetaken, uid, formattedDateTime);
        cRepository.save(call);

        return response;
    }

    public ListSource getSourceResponse(String endpoint, String request, Long uid) throws JsonProcessingException {

        String timetaken = "";
        String responseString = cRepository.getResponse(request);
        ListSource response = new ListSource();

        if (responseString == null || responseString.isEmpty()) {

            Long startTime = System.nanoTime();

            response = template.getForObject(
                    request,  // url to the api
                    ListSource.class  // the expected response type is Article[]
            );

            Long endTime = System.nanoTime();
            timetaken = String.valueOf((endTime - startTime));
            responseString = String.valueOf(response);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(responseString, ListSource.class);

            timetaken = cRepository.getTimeTaken(request);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        Call call = new Call(endpoint, request, responseString, timetaken, uid, formattedDateTime);
        cRepository.save(call);

        return response;
    }
}
