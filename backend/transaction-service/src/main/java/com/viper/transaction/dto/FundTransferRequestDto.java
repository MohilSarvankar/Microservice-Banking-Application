package com.viper.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FundTransferRequestDto {
	
	private String fromAccount;
	private String toAccount;
	private Long amount;
}
