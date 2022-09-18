package com.skywalker.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skywalker.reddit.entity.Vote;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, String>{

}
