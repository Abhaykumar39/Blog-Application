package com.blog.Blog_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Blog_api.entities.Comment;
import com.blog.Blog_api.payloads.ApiResponse;
import com.blog.Blog_api.payloads.CommentDto;
import com.blog.Blog_api.services.CommentService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId) {
        CommentDto createComment = this.commentService.createComment(comment, postId);

        return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted SuccessFully", true), HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer commentId) {

        CommentDto updateComment = this.commentService.updateComment(commentDto, commentId);

        return ResponseEntity.ok(updateComment);
    }
}
