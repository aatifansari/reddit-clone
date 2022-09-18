package com.skywalker.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skywalker.reddit.entity.Subreddit;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long>{

}
