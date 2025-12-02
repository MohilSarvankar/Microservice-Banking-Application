package com.viper.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viper.auth.model.User;
import com.viper.auth.service.AuthService;
import com.viper.common.dto.ApiResponse;
import com.viper.common.enums.ResponseStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(@RequestBody User user) {
		String token = authService.login(user);
		
		ApiResponse<String> response = new ApiResponse<>(
					ResponseStatus.SUCCESS,
					"Login successful",
					token
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {
		user = authService.register(user);
		
		ApiResponse<User> response = new ApiResponse<>(
					ResponseStatus.SUCCESS,
					"User created successfully",
					user
				);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/validate")
	public ResponseEntity<ApiResponse<Boolean>> validate(@RequestParam String token){
		Boolean isValid = authService.validate(token);
		
		ApiResponse<Boolean> response = new ApiResponse<>(
					ResponseStatus.SUCCESS,
					"Token validated successfully",
					isValid
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
