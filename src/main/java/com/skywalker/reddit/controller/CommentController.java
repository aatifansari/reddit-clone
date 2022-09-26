package com.skywalker.reddit.controller;

import com.skywalker.reddit.dto.CommentDto;
import com.skywalker.reddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    protected ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        commentService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getCommentsByPostId/{postId}")
    protected ResponseEntity<?> getCommentsForPost(@PathVariable String postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }

    @GetMapping("/getCommentsByUsername/{username}")
    protected ResponseEntity<?> getCommentsForUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentsByUsername(username));
    }

}
