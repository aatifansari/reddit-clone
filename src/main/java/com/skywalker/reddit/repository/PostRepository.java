package com.skywalker.reddit.repository;

import com.skywalker.reddit.entity.Subreddit;
import com.skywalker.reddit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skywalker.reddit.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String>{

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
