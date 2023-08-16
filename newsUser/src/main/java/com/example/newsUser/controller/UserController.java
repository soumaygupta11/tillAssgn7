package com.example.newsUser.controller;

import com.example.newsUser.contract.CreateUserRequest;
import com.example.newsUser.contract.CreateUserResponse;
import com.example.newsUser.contract.EmailRequest;
import com.example.newsUser.service.EmailService;
import com.example.newsUser.service.LatencyMetricsService;
import com.example.newsUser.service.MetricsService;
import com.example.newsUser.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.Histogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    MetricsService metricsService;


    @GetMapping({"/ping"})
    @NotNull
    public ResponseEntity<?> ping() {
        metricsService.getCounterWithName("ping").labels("/ping").inc();

        Histogram.Timer requestTimer = metricsService.getHistogramWithName("pingLatency").labels("/ping").startTimer();

        ResponseEntity<?> response = ResponseEntity.ok("pong");

        requestTimer.observeDuration();

        return response;    }
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest){
        metricsService.getCounterWithName("user").labels("/user").inc();

        return Optional
                .ofNullable(userService.createUser(createUserRequest))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }



    @GetMapping("/news")
    public ResponseEntity<?> getNews(@RequestParam("uid") Long uid) throws JsonProcessingException {
        metricsService.getCounterWithName("news").labels("/news").inc();

        return Optional
                .ofNullable(userService.getNews(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/newsLimited")
    public ResponseEntity<?> getNews(@RequestParam("uid") Long uid, @RequestParam("max_articles") Integer max_articles) throws JsonProcessingException{
        metricsService.getCounterWithName("newsLimited").labels("/newsLimited").inc();

        return Optional
                .ofNullable(userService.getNewsLimited(uid, max_articles))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/sources")
    public ResponseEntity<?> getSources(@RequestParam("uid") Long uid)  throws JsonProcessingException {
        metricsService.getCounterWithName("sources").labels("/sources").inc();

        return Optional
                .ofNullable(userService.getSources(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/addPrefSource")
    public ResponseEntity<?> addSource(@RequestParam("uid") Long uid, @RequestParam("source") String source) throws JsonProcessingException{
        metricsService.getCounterWithName("addPrefSource").labels("/addPrefSource").inc();

        return Optional
                .ofNullable(userService.addPrefSource(uid, source))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping ("/sourceBasedNews")
    public ResponseEntity<?> sourceBasedNews(@RequestParam("uid") Long uid) throws JsonProcessingException{
        metricsService.getCounterWithName("sourceBasedNews").labels("/sourceBasedNews").inc();
            return Optional
                .ofNullable(userService.sourceBasedNews(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
//
    @GetMapping ("/newsByDate")
    public ResponseEntity<?> newsByDate(@RequestParam("uid") Long uid , @RequestParam("from") String from, @RequestParam("to") String to) throws JsonProcessingException{
        metricsService.getCounterWithName("newsByDate").labels("/newsByDate").inc();
        return Optional
                .ofNullable(userService.newsByDate(uid, from, to))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//        return userRepository.newsByDate(from, to);
    }

    @GetMapping ("/report")
    public ResponseEntity<?> report(){
        metricsService.getCounterWithName("report").labels("/report").inc();
        return Optional
                .ofNullable(userService.report())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/sendNewsByEmail")
    public ResponseEntity<?> sendNewsByEmail(@RequestParam("uid") Long uid) throws JsonProcessingException{
        metricsService.getCounterWithName("sendNewsByEmail").labels("/sendNewsByEmail").inc();
        return Optional
                .ofNullable(userService.sendNewsByEmail(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam("uid") Long uid) {
        metricsService.getCounterWithName("subscribe").labels("/subscribe").inc();
        return Optional
                .ofNullable(userService.subscribe(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/unsubscribe")
        public ResponseEntity<?> unsubscribe(@RequestParam("uid") Long uid) {
        metricsService.getCounterWithName("unsubscribe").labels("/unsubscribe").inc();
        return Optional
                .ofNullable(userService.unsubscribe(uid))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/bill")
    public ResponseEntity<?> bill() {
        metricsService.getCounterWithName("bill").labels("/bill").inc();
        return Optional
                .ofNullable(userService.bill())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/topUsers")
    public ResponseEntity<?> topUsers() {
        metricsService.getCounterWithName("topUsers").labels("/topUsers").inc();
        return Optional
                .ofNullable(userService.topUsers())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


}
