package com.viper.common.dto;

import com.viper.common.enums.ResponseStatus;

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
