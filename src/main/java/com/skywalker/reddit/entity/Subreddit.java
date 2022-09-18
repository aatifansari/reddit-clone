package com.skywalker.reddit.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subreddit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message ="Community name id required")
	private String name;

	@NotBlank(message="Description is required")
	private String description;

	@OneToMany(fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<Post> posts;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	private Instant createdDate = Instant.now();

	@LastModifiedBy
	private String updatedBy;

	@LastModifiedDate
	private Instant updatedDate = Instant.now();

}
