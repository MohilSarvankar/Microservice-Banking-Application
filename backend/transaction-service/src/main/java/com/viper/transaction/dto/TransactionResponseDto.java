package com.viper.transaction.dto;

import com.viper.transaction.enums.TransactionStatus;

import lombok.Data;

@Data
public class TransactionResponseDto {
	
	private String transactionRef;
	private String fromAccount;
	private String toAccount;
	private Long amount;
	private TransactionStatus status;
	private String failureReason;
	
}
