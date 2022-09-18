package com.skywalker.reddit.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
	@Id
	private String voteId;
	private VoteType voteType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="postId", referencedColumnName ="postId")
	private Post post;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId", referencedColumnName = "userId")
	private User user;

}
