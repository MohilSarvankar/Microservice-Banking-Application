package com.viper.account.model;

import lombok.Data;

@Data
public class FundTransferRequestDto {
	
	private String fromAccount;
	private String toAccount;
	private long amount;
}
