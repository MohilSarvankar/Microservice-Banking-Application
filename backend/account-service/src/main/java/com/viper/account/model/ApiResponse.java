package com.viper.account.model;

import com.viper.account.enums.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	private ResponseStatus status;
	private String message;
	private T data;
}
