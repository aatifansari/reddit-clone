package com.skywalker.reddit.controller;

import com.skywalker.reddit.dto.SubredditDto;
import com.skywalker.reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<?> createSubreddit(@RequestBody @Validated SubredditDto subredditDto){
//    return new ResponseEntity<>(subredditService.save(subredditDto), HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return new ResponseEntity<>(subredditService.getAllSubreddit(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubredditById(@PathVariable("id") Long id){
        return new ResponseEntity<>(subredditService.getSubredditById(id), HttpStatus.OK);
    }
}
