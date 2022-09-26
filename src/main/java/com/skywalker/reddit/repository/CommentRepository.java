package com.skywalker.reddit.repository;

import com.skywalker.reddit.entity.Post;
import com.skywalker.reddit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.skywalker.reddit.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String>{

    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUser(User user);
}
