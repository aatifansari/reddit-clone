package com.skywalker.reddit.service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.skywalker.reddit.dto.AuthenticationResponse;
import com.skywalker.reddit.dto.LoginRequest;
import com.skywalker.reddit.exception.InvalidTokenException;
import com.skywalker.reddit.exception.SpringRedditException;
import com.skywalker.reddit.security.JwtProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.skywalker.reddit.dto.RegisterRequest;
import com.skywalker.reddit.entity.User;
import com.skywalker.reddit.entity.VerificationToken;
import com.skywalker.reddit.repository.UserRepository;
import com.skywalker.reddit.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreatedDate(Instant.now());
		user.setEnabled(true);
		
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
//		mailService.sendMail(new NotificationEmail("Please Activate your Account",
//				user.getEmail(),"Thank you for signing up to Spring Reddit, "
//						+ "please click on the below url to activate your account : "
//						+ "https://locahost:8080/api/auth/accountVerification/"+token));
	}
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
//		VerificationToken verificationToken = new VerificationToken();
//		verificationToken.setId(UUID.randomUUID().toString());
//		verificationToken.setToken(token);
//		verificationToken.setUser(user);
//		verificationToken.setExpiryDate(Instant.now().plus(24, ChronoUnit.HOURS));
//		verificationToken.set
		VerificationToken verificationToken = VerificationToken.builder()
						.id(UUID.randomUUID().toString())
								.token(token)
										.user(user)
												.expiryDate(Instant.now().plus(24,ChronoUnit.HOURS))
														.credtedDate(Instant.now())
																.build();
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	@Transactional()
	public User getCurrentUser() {
		Jwt principal = (Jwt) SecurityContextHolder.
				getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(principal.getSubject())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
	}

	public void verifyAccount(String token) {

		VerificationToken vToken = verificationTokenRepository.
				findByToken(token).orElseThrow(()-> new InvalidTokenException("Invalid Token"));
		if(Duration.between(vToken.getExpiryDate(),vToken.getCredtedDate()).toHours()>24){
			throw new InvalidTokenException("Token has expired");
		}
		fetchUserAndEnable(vToken);
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken){
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(()->
				new SpringRedditException("User not found"));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String jwtToken = jwtProvider.generateToken(authenticate);
		return new AuthenticationResponse(jwtToken, loginRequest.getUsername());
	}

//	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
//		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
//		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
//		return AuthenticationResponse.builder()
//				.authenticationToken(token)
//				.refreshToken(refreshTokenRequest.getRefreshToken())
//				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
//				.username(refreshTokenRequest.getUsername())
//				.build();
//	}

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
}
