package com.skywalker.reddit.controller;

import com.skywalker.reddit.dto.AuthenticationResponse;
import com.skywalker.reddit.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.skywalker.reddit.dto.RegisterRequest;
import com.skywalker.reddit.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Validated RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
	}
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
	}
	@PostMapping("/signin")
	public AuthenticationResponse signin(@RequestBody @Validated LoginRequest loginRequest){
		return authService.login(loginRequest);

	}
}
