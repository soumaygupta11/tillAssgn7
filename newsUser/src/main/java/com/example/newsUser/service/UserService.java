package com.example.newsUser.service;


import com.example.newsUser.contract.CreateUserRequest;
import com.example.newsUser.contract.CreateUserResponse;
import com.example.newsUser.contract.EmailRequest;
import com.example.newsUser.contract.client.RestClient;
import com.example.newsUser.contract.newsArticleWrapper.Article;
import com.example.newsUser.contract.newsArticleWrapper.newsArticle;
import com.example.newsUser.contract.sourceWrapper.ListSource;
import com.example.newsUser.contract.sourceWrapper.Source;
import com.example.newsUser.exception.UserNotFoundException;
import com.example.newsUser.model.entity.*;
import com.example.newsUser.model.repository.CRepository;
import com.example.newsUser.model.repository.SourceRepository;
import com.example.newsUser.model.repository.SubscriptionRespository;
import com.example.newsUser.model.repository.URepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class UserService {
    @Autowired
    private URepository uRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SubscriptionRespository subscriptionRespository;
    @Autowired
    private CRepository cRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private RestClient restClient;


    public UserService() {
    }


    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        User user  = new User(createUserRequest.getEmail(), createUserRequest.getSelected_country(), createUserRequest.getSelected_category(), null);

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(!Pattern.matches(regexPattern, user.getEmail())){
            return null;
        }



        List<String> countries = Arrays.asList("ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","us","ve","za");
        List<String> categories = Arrays.asList("business","entertainment","general","health","science","sports","technology");

        if(!countries.contains(user.getSelected_country())){
            return null;
        }
        if(!categories.contains(user.getSelected_category())){
            return null;
        }

        uRepository.save(user);
        EmailRequest emailRequest = new EmailRequest(user.getEmail(), "Welcome to NewsApi", "Thank you for signing up to NewsApp. You will now receive daily news updates from your selected country and category.");
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());

        CreateUserResponse createUserResponse = new CreateUserResponse(user.getUniqueID());
        return createUserResponse;

    }


    public List<Article> getNews(Long uid) throws JsonProcessingException {
        String url = "https://newsapi.org/v2/top-headlines?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = "https://newsapi.org/v2/top-headlines";
            String requestString = url + country +"&category="+category+"&apiKey=4f61476a90114ef699db7c728639def2";

            newsArticle response = restClient.getArticleResponse(endpoint, requestString, uid);

            return response.getArticles();
        }
        throw new UserNotFoundException("User not found");


    }





    public List<Article> getNewsLimited(Long uid, Integer max_articles) throws JsonProcessingException{
        String url = "https://newsapi.org/v2/top-headlines?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();

            String endpoint = "https://newsapi.org/v2/top-headlines";
            String requestString = url + country +"&category="+category+"&apiKey=4f61476a90114ef699db7c728639def2&pageSize="+max_articles;

            newsArticle response = restClient.getArticleResponse(endpoint, requestString, uid);

            return response.getArticles();
        }
        throw new UserNotFoundException("User not found");

    }



    public List<Source> getSources(Long uid) throws JsonProcessingException{
        String url = "https://newsapi.org/v2/top-headlines/sources?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = "https://newsapi.org/v2/top-headlines/sources";
            String requestString = url + country +"&category="+category+"&apiKey=4f61476a90114ef699db7c728639def2";

            ListSource response = restClient.getSourceResponse(endpoint, requestString, uid);

            return response.getSources();
        }
        throw new UserNotFoundException("User not found");


    }


    public ResponseEntity<?> addPrefSource(Long uid, String source_id) throws JsonProcessingException{
        String url = "https://newsapi.org/v2/top-headlines/sources?country=";
        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = "https://newsapi.org/v2/top-headlines/sources";
            String requestString = url + country +"&category="+category+"&apiKey=4f61476a90114ef699db7c728639def2";

            ListSource response = restClient.getSourceResponse(endpoint, requestString, uid);

            List<Source> sources = response.getSources();
            for(Source source : sources){
                if(source.getId().equals(source_id)){

                    PrefSources prefSource = new PrefSources(user, source_id);
                    sourceRepository.save(prefSource);

                    return new ResponseEntity<>(source_id, HttpStatus.OK);
                }
            }

            return null;

        }
        throw new UserNotFoundException("User not found");


    }



    public List<Source> sourceBasedNews(Long uid) throws JsonProcessingException{
        String url = "https://newsapi.org/v2/top-headlines/sources?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = "https://newsapi.org/v2/top-headlines/sources";
            String requestString = url + country +"&category="+category+"&apiKey=4f61476a90114ef699db7c728639def2";

            ListSource response = restClient.getSourceResponse(endpoint, requestString, uid);

            List<Source> all_articles = response.getSources();
            List<Source> pref_articles = new ArrayList<>();

            List<PrefSources> prefSources = sourceRepository.findAll();

            for(PrefSources prefSource : prefSources){
                for(Source article : all_articles) {
                    if (article.getId().equals(prefSource.getSource())) {
                        pref_articles.add(article);
                    }
                }
            }

            if(prefSources.size()==0)   return all_articles;
            else
                return pref_articles;
        }
        throw new UserNotFoundException("User not found");
    }


    public List<Article> newsByDate(Long uid, String from, String to) throws JsonProcessingException{
        String url = "https://newsapi.org/v2/top-headlines?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = "https://newsapi.org/v2/top-headlines";
            String requestString = url + country +"&category="+category+"&apiKey=4f61476a90114ef699db7c728639def2";

            newsArticle response = restClient.getArticleResponse(endpoint, requestString, uid);

            List<Article> all_articles = response.getArticles();
            List<Article> inDate_articles = new ArrayList<>();

            for(Article article : all_articles){
                if(article.getPublishedAt().substring(0,10).compareTo(from) >= 0 && article.getPublishedAt().substring(0,10).compareTo(to) <= 0){
                    inDate_articles.add(article);
                }
            }

            return inDate_articles;
        }
        throw new UserNotFoundException("User not found");

    }

    public ResponseEntity<?> sendNewsByEmail(Long uid) throws JsonProcessingException{
        String url = "https://newsapi.org/v2/top-headlines?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();
            String email = user.getEmail();

            String endpoint = "https://newsapi.org/v2/top-headlines";
            String requestString = url + country + "&category=" + category + "&apiKey=4f61476a90114ef699db7c728639def2";

            newsArticle response = restClient.getArticleResponse(endpoint, requestString, uid);

            List<Article> all_articles = response.getArticles();
            String topHeadlines = "";
            for (Article article : all_articles) {
                topHeadlines += article.getTitle() + "\n" + article.getDescription() + "\n" + article.getUrl() + "\n\n";
            }

            String subject = "News for you";
            EmailRequest emailRequest = new EmailRequest(user.getEmail(), subject, topHeadlines);
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());

            return ResponseEntity.ok("News sent successfully");

        }
        throw new UserNotFoundException("User not found");
    }

    public ResponseEntity<?> subscribe(Long uid){
        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Long user_id = user.getUniqueID();
            Optional<Subscription> subscriptionOptional = subscriptionRespository.findById(user_id);
            if(subscriptionOptional.isPresent()){
                Subscription subscription = subscriptionOptional.get();
                if(subscription.getIs_subscribed()){
                    return ResponseEntity.ok("Already subscribed");
                }
                else{
                    subscriptionRespository.subscribe(uid);
                    return ResponseEntity.ok("Subscribed successfully");
                }
            }
            subscriptionRespository.save(new Subscription(user_id, true));
            return ResponseEntity.ok("Subscribed successfully");
        }
        throw new UserNotFoundException("User not found");
    }

    public ResponseEntity<?> unsubscribe(Long uid){
        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Long user_id = user.getUniqueID();
            Optional<Subscription> subscriptionOptional = subscriptionRespository.findById(user_id);
            if(subscriptionOptional.isPresent()){
                Subscription subscription = subscriptionOptional.get();
                if(subscription.getIs_subscribed()){
                    subscriptionRespository.unSubscribe(uid);
                    return ResponseEntity.ok("Unsubscribed successfully");
                }
                else{
                    return ResponseEntity.ok("Already unsubscribed");
                }
            }
            subscriptionRespository.save(new Subscription(user_id, false));
            return ResponseEntity.ok("Unsubscribed successfully");
        }
        throw new UserNotFoundException("User not found");
    }


    public ResponseEntity<?> report(){
        List<Call> calls = cRepository.findAll();

        Long totaltime = 0L;
        Long totalreschars = 0L;
        Long totalreqchars = 0L;
        for(Call call : calls){
            totalreschars += call.getResponse().length();
            totaltime += Long.parseLong(call.getTimetaken());
            totalreqchars += call.getRequest().length();
        }
        Long avgreqchars = totalreqchars/calls.size();
        Long avgreschars = totalreschars/calls.size();
        Long avgtime = totaltime/calls.size();
        HashMap<String, String> map = new HashMap<>();
        map.put("Average time taken( in nanoseconds ) :", avgtime.toString());
        map.put("Total number of requests :", String.valueOf(calls.size()));
        map.put("Average number of characters in request :", avgreqchars.toString());
        map.put("Average number of characters in response :", avgreschars.toString());

        return ResponseEntity.ok(map);

    }

    public double bill(){
        Long countE = 0L;
        Long countT = 0L;
        Long countS = 0L;


        List<EndpointCount> endpointCounts = cRepository.getEndpointCount();
        for(EndpointCount endpointCount : endpointCounts){
            if(endpointCount.getEndpoint().equals("https://newsapi.org/v2/everything")){
                countE = endpointCount.getCount();
            }
            else if(endpointCount.getEndpoint().equals("https://newsapi.org/v2/top-headlines")){
                countT = endpointCount.getCount();
            }
            else if(endpointCount.getEndpoint().equals("https://newsapi.org/v2/top-headlines/sources")){
                countS = endpointCount.getCount();
            }
        }


        return (countE*10.5)+(countT*5.5)+(countS*200);
    }


    public List<User> topUsers(){
        List<Long> topUserIds = cRepository.getTopUsers();
        List<User> topUsers = new ArrayList<>();
        for(Long id : topUserIds){
            Optional<User> userOptional = uRepository.findById(id);
            if(userOptional.isPresent()){
                topUsers.add(userOptional.get());
            }
        }

        return topUsers;

    }

}
