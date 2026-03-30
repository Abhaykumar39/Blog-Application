package com.blog.Blog_api.services.impl;

import com.blog.Blog_api.entities.User;
import com.blog.Blog_api.exception.ResourceNotFoundException;
import com.blog.Blog_api.payloads.UserDto;
import com.blog.Blog_api.repositories.UserRepo;
import com.blog.Blog_api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();

        return users.stream()
                .map(this::userToDto)
                .toList();
    }

    @Override
    public void deleteUser(Integer userID) {

        User user = this.userRepo.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userID));

        this.userRepo.delete(user);
    }

    // DTO → Entity
    private User dtoToUser(UserDto userDto) {
        //Using Model Mapper
        User user;
        user = this.modelMapper.map(userDto,User.class);
//        User user = new User();
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    // Entity → DTO
    private UserDto userToDto(User user) {
        //Using Model Mapper
        UserDto userdto;
        userdto= this.modelMapper.map(user,UserDto.class);
        return  userdto;
//        UserDto dto = new UserDto();
//        dto.setId(user.getId());
//        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        dto.setAbout(user.getAbout());
//        dto.setPassword(user.getPassword());
//        return dto;
    }
}