package com.skywalker.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	@NotBlank(message = "Email required")
	private String email;
	@NotBlank(message = "Username required")
	private String username;
	@NotBlank(message =  "password required")
	private String password;
}
