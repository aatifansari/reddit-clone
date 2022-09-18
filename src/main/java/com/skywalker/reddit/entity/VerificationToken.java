package com.skywalker.reddit.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name="token")
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
	
	@Id
	private String id;
	private String token;
	@OneToOne(fetch= FetchType.LAZY)
	private User user;
	private Instant expiryDate;
	private Instant credtedDate;

}
