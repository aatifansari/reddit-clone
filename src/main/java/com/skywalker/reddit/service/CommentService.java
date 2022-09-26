package com.skywalker.reddit.service;

import com.skywalker.reddit.dto.CommentDto;
import com.skywalker.reddit.entity.Comment;
import com.skywalker.reddit.entity.Post;
import com.skywalker.reddit.entity.User;
import com.skywalker.reddit.repository.CommentRepository;
import com.skywalker.reddit.repository.PostRepository;
import com.skywalker.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(CommentDto commentDto){
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(EntityNotFoundException::new);
        User user = authService.getCurrentUser();
        commentRepository.save(this.populateCommentEntity(commentDto, post, user));
    }

    public List<CommentDto> getCommentsByPostId(String postId){
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments.stream().map(this::populateCommentDto).collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        List<Comment> comments = commentRepository.findAllByUser(user);
        return comments.stream().map(this::populateCommentDto).collect(Collectors.toList());
    }

    Comment populateCommentEntity(CommentDto commentDto, Post post, User user){
        return Comment.builder()
                .id(UUID.randomUUID().toString())
                .text(commentDto.getText())
                .post(post)
                .user(user)
                .createdBy(user.getUserId())
                .updatedBy(user.getUserId())
                .build();
    }

    CommentDto populateCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .postId(comment.getPost().getPostId())
                .username(comment.getUser().getUsername())
                .build();
    }
}
