package com.blog.Blog_api.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Blog_api.entities.Comment;
import com.blog.Blog_api.entities.Post;
import com.blog.Blog_api.exception.ResourceNotFoundException;
import com.blog.Blog_api.payloads.CommentDto;
import com.blog.Blog_api.payloads.PostDto;
import com.blog.Blog_api.repositories.CommentRepo;
import com.blog.Blog_api.repositories.PostRepo;
import com.blog.Blog_api.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));

        this.commentRepo.delete(com);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {

        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));

        comment.setContent(commentDto.getContent());

        Comment updatedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(updatedComment, CommentDto.class);
    }
}
