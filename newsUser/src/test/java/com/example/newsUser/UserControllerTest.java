package com.example.newsUser;


import com.example.newsUser.contract.CreateUserRequest;
import com.example.newsUser.contract.CreateUserResponse;
import com.example.newsUser.controller.UserController;
import com.example.newsUser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        CreateUserRequest createUserRequest = new CreateUserRequest(/* initialize your request data */);

        CreateUserResponse createUserResponse = new CreateUserResponse(Long.valueOf(1));
        // Mock userService.createUser() to return a response
        when(userService.createUser(createUserRequest)).thenReturn(createUserResponse);

        ResponseEntity<?> responseEntity = userController.createUser(createUserRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateUser_Failure() {
        CreateUserRequest createUserRequest = new CreateUserRequest(/* initialize your request data */);

        // Mock userService.createUser() to return null, simulating a failure
        when(userService.createUser(createUserRequest)).thenReturn(null);

        ResponseEntity<?> responseEntity = userController.createUser(createUserRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
