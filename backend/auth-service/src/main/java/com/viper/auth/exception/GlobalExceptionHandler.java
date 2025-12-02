package com.viper.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.viper.auth.enums.ResponseStatus;
import com.viper.auth.model.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
		ApiResponse<Object> response = new ApiResponse<>(
				ResponseStatus.ERROR,
				e.getMessage(),
				null
				);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> exceptionHandler(Exception e){
		ApiResponse<Object> response = new ApiResponse<>(
				ResponseStatus.ERROR,
				e.getMessage(),
				null
				);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
}
