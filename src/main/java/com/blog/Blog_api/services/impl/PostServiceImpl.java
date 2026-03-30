package com.blog.Blog_api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Blog_api.entities.Category;
import com.blog.Blog_api.entities.Post;
import com.blog.Blog_api.entities.User;
import com.blog.Blog_api.exception.ResourceNotFoundException;
import com.blog.Blog_api.payloads.CategoryDto;
import com.blog.Blog_api.payloads.PostDto;
import com.blog.Blog_api.payloads.UserDto;
import com.blog.Blog_api.repositories.CategoryRepo;
import com.blog.Blog_api.repositories.PostRepo;
import com.blog.Blog_api.repositories.UserRepo;
import com.blog.Blog_api.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    // CREATE POST
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        // 1. Fetch user
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        // 2. Fetch category
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        // 3. DTO → Entity
        Post post = this.modelMapper.map(postDto, Post.class);

        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    // UPDATE
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepo.save(post);

        return modelMapper.map(updatedPost, PostDto.class);
    }

    // DELETE
    @Override
    public void deletePost(Integer postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        postRepo.delete(post);
    }

    // GET ALL
    @Override
    public List<PostDto> getAllPost() {

        List<Post> allPosts = this.postRepo.findAll();

        List<PostDto> postDtos = allPosts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    // GET SINGLE
    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        return this.modelMapper.map(post, PostDto.class);

    }

    // BY CATEGORY
    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));

        List<Post> posts = this.postRepo.findByCategory(cat);

        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<Post> posts = postRepo.findByUser(user);

        return posts.stream().map(post -> {

            PostDto dto = new PostDto();

            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setImageName(post.getImageName());
            dto.setAddedDate(post.getAddedDate());
            dto.setUser(modelMapper.map(post.getUser(), UserDto.class));
            dto.setCategory(modelMapper.map(post.getCategory(), CategoryDto.class));

            return dto;

        }).toList();
    }

    // SEARCH
    @Override
    public List<Post> searchPost(String keyword) {

        return postRepo.findByTitleContaining(keyword);
    }
}