package com.viper.transaction.dto;

import lombok.Data;

@Data
public class TransactionRequestDto {
	
	private String fromAccount;
	private String toAccount;
	private Long amount;
	private String remarks;
}
