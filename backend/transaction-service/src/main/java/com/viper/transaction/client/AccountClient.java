package com.viper.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.viper.common.dto.ApiResponse;
import com.viper.transaction.dto.FundTransferRequestDto;

@FeignClient(name = "account-service", path="/account")
public interface AccountClient {
	
	@PostMapping("/transfer")
	public ResponseEntity<ApiResponse<Object>> transferFunds(@RequestBody FundTransferRequestDto request);
}
