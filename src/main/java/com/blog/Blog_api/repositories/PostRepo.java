package com.blog.Blog_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.Blog_api.entities.Post;
import com.blog.Blog_api.entities.User;
import com.blog.Blog_api.entities.Category;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String keyword);
}