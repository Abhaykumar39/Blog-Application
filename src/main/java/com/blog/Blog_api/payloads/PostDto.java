package com.blog.Blog_api.payloads;

import java.util.Date;
import java.util.Locale.Category;

import com.blog.Blog_api.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

}
