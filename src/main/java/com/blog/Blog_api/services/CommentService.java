package com.blog.Blog_api.services;

import com.blog.Blog_api.entities.Comment;
import com.blog.Blog_api.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto comment,Integer postId);

    void deleteComment(Integer commentId);

    CommentDto updateComment(CommentDto commentDto,Integer postId);
    
}
