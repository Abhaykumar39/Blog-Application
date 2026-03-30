package com.blog.Blog_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.Blog_api.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    
}
