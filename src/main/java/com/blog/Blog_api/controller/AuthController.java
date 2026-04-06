package com.blog.Blog_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Blog_api.Security.JwtTokenProvider;
import com.blog.Blog_api.entities.User;
import com.blog.Blog_api.payloads.JwtAuthRequest;
import com.blog.Blog_api.payloads.JwtAuthResponse;
import com.blog.Blog_api.services.UserService;

import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> signin(@RequestBody JwtAuthRequest req) {
        String email = req.getUsername();
        String password = req.getPassword();
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String token = JwtTokenProvider.generateJwtToken(user.get());
            return new ResponseEntity<>(
                    JwtAuthResponse.builder().token(token)
                            .message("Login successful")
                            .build(),
                    HttpStatus.OK);
        } else if (user.isPresent())
            return new ResponseEntity<>(JwtAuthResponse.builder().message("Wrong password").build(),
                    HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(JwtAuthResponse.builder().message("This email is not registered").build(),
                HttpStatus.UNAUTHORIZED);
    }

    // @PostMapping("/register")
    // public ResponseEntity<AuthResponse> signupUser(@RequestBody RegisterRequest registerRequest) {
    //     String email = registerRequest.getEmail();
    //     if (userService.findUserByEmail(email).isPresent()) {
    //         return ResponseEntity.badRequest().body(AuthResponse.builder().message("Email already exists").build());
    //     }
    //     registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    //     User savedUser = userService.createUser(registerRequest);
    //     String token = JwtTokenProvider.generateJwtToken(savedUser);
    //     return ResponseEntity
    //             .ok(AuthResponse.builder().jwt(token).message("Success").senderId(savedUser.getUserId())
    //                     .userName(savedUser.getUsername())
    //                     .build());
    // }

}

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.*;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.web.bind.annotation.*;

// import com.blog.Blog_api.Security.JwtTokenHelper;
// import com.blog.Blog_api.payloads.JwtAuthRequest;
// import com.blog.Blog_api.payloads.JwtAuthResponse;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     @Autowired
//     private JwtTokenHelper jwtTokenHelper;

//     @Autowired
//     private UserDetailsService userDetailsService;

//     @Autowired
//     private AuthenticationManager authenticationManager;

//     //LOGIN API
//     @PostMapping("/login")
//     public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {

//         this.authenticate(request.getUsername(), request.getPassword());

//         UserDetails userDetails = this.userDetailsService
//                 .loadUserByUsername(request.getUsername());

//         String token = this.jwtTokenHelper.generateToken(userDetails);

//         JwtAuthResponse response = new JwtAuthResponse();
//         response.setToken(token);

//         return new ResponseEntity<>(response, HttpStatus.OK);
//     }

//     // AUTH METHOD
//     private void authenticate(String username, String password) {

//         UsernamePasswordAuthenticationToken authToken =
//                 new UsernamePasswordAuthenticationToken(username, password);

//         try {
//             this.authenticationManager.authenticate(authToken);
//         } catch (BadCredentialsException e) {
//             throw new RuntimeException("Invalid Username or Password");
//         }
//     }
// }