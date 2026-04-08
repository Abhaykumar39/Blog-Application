package com.blog.Blog_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Blog_api.Security.JwtTokenHelper;
import com.blog.Blog_api.Security.JwtTokenProvider;
import com.blog.Blog_api.entities.User;
import com.blog.Blog_api.payloads.JwtAuthRequest;
import com.blog.Blog_api.payloads.JwtAuthResponse;
import com.blog.Blog_api.payloads.UserDto;
import com.blog.Blog_api.services.UserService;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {

        Optional<User> user = userService.findUserByEmail(request.getUsername());

        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {

            String token = jwtTokenHelper.generateToken(user.get());

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);
            response.setMessage("Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Register New User
    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody User user) {

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());

        UserDto registeredUser = userService.registerNewUser(userDto);

        String token = jwtTokenHelper.generateToken(
                new org.springframework.security.core.userdetails.User(
                        registeredUser.getEmail(),
                        registeredUser.getPassword(),
                        new ArrayList<>()));

            JwtAuthResponse response = JwtAuthResponse.builder()
                    .token(token)
                    .message("User registered successfully")
                    .user(registeredUser)
                    .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
