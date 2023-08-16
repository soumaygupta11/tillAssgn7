package com.example.newsUser;

import com.example.newsUser.contract.CreateUserRequest;
import com.example.newsUser.contract.CreateUserResponse;
import com.example.newsUser.contract.newsArticleWrapper.Article;
import com.example.newsUser.contract.newsArticleWrapper.newsArticle;
import com.example.newsUser.model.entity.Subscription;
import com.example.newsUser.model.entity.User;
import com.example.newsUser.model.repository.CRepository;
import com.example.newsUser.model.repository.SubscriptionRespository;
import com.example.newsUser.model.repository.URepository;
import com.example.newsUser.service.EmailService;
import com.example.newsUser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private URepository userRepository;

    @Mock
    private CRepository cRepository;


    @Mock
    private SubscriptionRespository subscriptionRepository;

    @Mock
    private EmailService emailService;
    @BeforeEach



    @Test
    public void testSubscribe_Success() {
        Long userId = 1L;
        User user = new User("test@example.com", "us", "business", null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(subscriptionRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.subscribe(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subscribed successfully", response.getBody());
    }

    @Test
    public void testUnsubscribe_Success() {
        Long userId = 1L;
        User user = new User("test@example.com", "us", "business", null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(subscriptionRepository.findById(userId)).thenReturn(Optional.of(new Subscription(userId, true)));

        ResponseEntity<?> response = userService.unsubscribe(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Unsubscribed successfully", response.getBody());
    }



}


