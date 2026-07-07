package com.example.foodiesapi.service;

import com.example.foodiesapi.io.UserRequest;
import com.example.foodiesapi.io.UserResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> registerUser(UserRequest request);
    String findByUserId();
}
