package com.blog.Blog_api.services;

import java.util.List;

import com.blog.Blog_api.entities.Post;
import com.blog.Blog_api.payloads.PostDto;

public interface PostService {

    // create 
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    // Update
    PostDto updatePost(PostDto postDto, Integer postId);

    // delete
    void deletePost(Integer postId);

    // getAllPost
    List<PostDto> getAllPost();

    // getSingle Post
    PostDto getPostById(Integer postId);

    // getAll Post by category
    List<PostDto> getPostByCategory(Integer categoryId);

    // getAll Post By user
    List<PostDto> getPostsByUser(Integer userId);

    // search post
    List<Post> searchPost(String keyword);


   
}
