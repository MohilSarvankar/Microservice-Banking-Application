package com.viper.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viper.common.dto.ApiResponse;
import com.viper.common.enums.ResponseStatus;
import com.viper.transaction.dto.TransactionRequestDto;
import com.viper.transaction.dto.TransactionResponseDto;
import com.viper.transaction.enums.TransactionStatus;
import com.viper.transaction.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	private TransactionService txnService;
	
	public TransactionController(TransactionService txnService) {
		this.txnService = txnService;
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<ApiResponse<TransactionResponseDto>> transferFunds(@RequestBody TransactionRequestDto txnRequest){
		
		TransactionResponseDto txnResponse = txnService.transferFunds(txnRequest);
		ApiResponse<TransactionResponseDto> response = null;
		
		if(txnResponse.getStatus() == TransactionStatus.SUCCESS) {
			response = new ApiResponse<>(
					ResponseStatus.SUCCESS,
					"Transaction completed successfully",
					txnResponse
				);
		}
		else {
			response = new ApiResponse<>(
					ResponseStatus.ERROR,
					"Transaction failed",
					txnResponse
				);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
