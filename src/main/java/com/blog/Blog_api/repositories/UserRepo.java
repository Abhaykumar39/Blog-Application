package com.blog.Blog_api.repositories;

import com.blog.Blog_api.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer>{
    Optional<User> findByEmail(String email);
}
