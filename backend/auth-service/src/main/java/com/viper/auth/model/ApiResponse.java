package com.viper.auth.model;

import com.viper.auth.enums.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	private ResponseStatus status;
	private String message;
	private T data;
}
