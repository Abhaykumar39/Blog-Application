package com.blog.Blog_api.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;
}