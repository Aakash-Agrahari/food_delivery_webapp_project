package com.example.foodiesapi.controller;
import com.example.foodiesapi.Util.JwtUtil;
import com.example.foodiesapi.entity.UserEntity;
import com.example.foodiesapi.io.AuthenticationRequest;
import com.example.foodiesapi.io.AuthenticationResponse;
import com.example.foodiesapi.repository.UserRepository;
import com.example.foodiesapi.service.AppUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService userDetailService;
    private final JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            // return 401 instead of 403 and show message
            return ResponseEntity.status(401).body("Invalid email or password");
        }

//        Optional<UserEntity> OptionalUser = userRepository.findByEmail(request.getEmail());
//       if (OptionalUser.isEmpty()){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with provided email");
//        }
//        UserEntity user = OptionalUser.get();
//        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect password");
//        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        //return new AuthenticationResponse(request.getEmail(), jwtToken);
        return ResponseEntity.ok(new AuthenticationResponse(request.getEmail(), jwtToken));
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "It's working";
    }
}
