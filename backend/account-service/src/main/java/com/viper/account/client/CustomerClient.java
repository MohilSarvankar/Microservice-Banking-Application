package com.viper.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.viper.account.config.FeignConfig;
import com.viper.account.model.CustomerDto;
import com.viper.common.dto.ApiResponse;

@FeignClient(name = "customer-service", path = "/customer", configuration = FeignConfig.class)
public interface CustomerClient {
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CustomerDto>> getCustomer(@PathVariable long id);
}
