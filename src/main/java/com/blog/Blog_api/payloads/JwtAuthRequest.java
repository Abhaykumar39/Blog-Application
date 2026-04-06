package com.blog.Blog_api.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;

    private String password;
    
}
