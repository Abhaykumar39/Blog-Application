package com.blog.Blog_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.Blog_api.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{  
    
}
