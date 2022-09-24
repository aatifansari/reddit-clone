package com.skywalker.reddit.service;

import com.skywalker.reddit.dto.PostRequest;
import com.skywalker.reddit.dto.PostResponse;
import com.skywalker.reddit.entity.Post;
import com.skywalker.reddit.entity.Subreddit;
import com.skywalker.reddit.entity.User;
import com.skywalker.reddit.repository.PostRepository;
import com.skywalker.reddit.repository.SubredditRepository;
import com.skywalker.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void save(PostRequest postRequest){
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditId())
                .orElseThrow(() -> new EntityNotFoundException("Subreddit does not exists"));
        User user = authService.getCurrentUser();
        Post post = this.populatePostEntity(postRequest, subreddit, user);
        postRepository.save(post);
    }

    @Transactional
    public PostResponse getPost(String postId){
        Post post = postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException("Post does not exists"));
        return this.populatePostResponse(post);
    }

    @Transactional
    public List<PostResponse> getAllPost(){
        return  postRepository.findAll()
                .stream()
                .map(this::populatePostResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getPostBySubreddit(Long subredditId){
       Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(()-> new EntityNotFoundException());
//       return subreddit.getPosts().stream().map(this::populatePostResponse).collect(Collectors.toList());
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(this::populatePostResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getPostByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException());
        return postRepository.findByUser(user)
                .stream()
                .map(this::populatePostResponse)
                .collect(Collectors.toList());
    }

    private Post populatePostEntity(PostRequest postRequest, Subreddit subreddit, User user) {
        return Post.builder()
                .postId(UUID.randomUUID().toString())
                .postName(postRequest.getPostName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .user(user)
                .subreddit(subreddit)
                .createdBy(user.getUserId())
                .createdDate(Instant.now())
                .updatedBy(user.getUserId())
                .updatedDate(Instant.now())
                .build();
    }

    private PostResponse populatePostResponse(Post post){
        return PostResponse.builder()
                .postId(post.getPostId())
                .postName(post.getPostName())
                .url(post.getUrl())
                .description(post.getDescription())
                .userName(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .voteCount(post.getVoteCount())
                .build();
    }
}
