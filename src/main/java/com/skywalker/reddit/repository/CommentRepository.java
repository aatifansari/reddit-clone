package com.skywalker.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skywalker.reddit.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String>{
	

}
