package com.viper.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.viper.auth.exception.ResourceNotFoundException;
import com.viper.auth.model.User;
import com.viper.auth.repo.AuthRepo;

@Service
public class AuthService {
	
	private AuthRepo authRepo;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	
	public AuthService(AuthRepo authRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.authRepo = authRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public User register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return authRepo.save(user);
	}

	public String login(User user) {
		User dbUser = authRepo.findByUsername(user.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		if(!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		return jwtService.generateToken(user);
	}

	public Boolean validate(String token) {
		return jwtService.validate(token);
	}

}
