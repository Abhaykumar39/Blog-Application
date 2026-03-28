package com.blog.Blog_api.services;

import com.blog.Blog_api.entities.User;
import com.blog.Blog_api.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userID);

}
