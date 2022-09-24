package com.skywalker.reddit.controller;

import com.skywalker.reddit.dto.PostRequest;
import com.skywalker.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPost(),HttpStatus.OK);
    }

    @GetMapping("/getPostsBySubredditId/{id}")
    public ResponseEntity<?> getPostBySubreddit(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPostBySubreddit(id), HttpStatus.OK);
    }

    @GetMapping("/getPostsByUserName/{username}")
    public ResponseEntity<?> getPostsByUsername(@PathVariable String username){
        return new ResponseEntity<>(postService.getPostByUsername(username),HttpStatus.OK);
    }
}
