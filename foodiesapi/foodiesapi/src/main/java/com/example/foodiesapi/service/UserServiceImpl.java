package com.example.foodiesapi.service;

import com.example.foodiesapi.entity.UserEntity;
import com.example.foodiesapi.io.UserRequest;
import com.example.foodiesapi.io.UserResponse;
import com.example.foodiesapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public ResponseEntity<?> registerUser(UserRequest request) {
        //UserEntity newUser = convertToEntity(request);
        //newUser = userRepository.save(newUser);
        //return convertToResponse(newUser);
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();
        UserEntity newUser = new UserEntity();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(passwordEncoder.encode(password));

        Optional<UserEntity> existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user with email exists");
        }

        newUser = userRepository.save(newUser);
        return ResponseEntity.status(201).body(newUser);

    }

    @Override
    public String findByUserId() {
        String loggegInUserEmail = authenticationFacade.getAuthentication().getName();
        UserEntity loggegInUser = userRepository.findByEmail(loggegInUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return loggegInUser.getId();
    }

    private UserEntity convertToEntity(UserRequest request){
        return UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }

    private UserResponse convertToResponse(UserEntity registeredUser){
        return UserResponse.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .build();
    }
}
