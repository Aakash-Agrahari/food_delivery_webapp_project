package com.example.foodiesapi.controller;

import com.example.foodiesapi.io.UserRequest;
import com.example.foodiesapi.io.UserResponse;
import com.example.foodiesapi.repository.UserRepository;
import com.example.foodiesapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody UserRequest request){
        return userService.registerUser(request);

    }
}
