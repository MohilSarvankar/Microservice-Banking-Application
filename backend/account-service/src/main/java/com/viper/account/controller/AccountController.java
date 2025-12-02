package com.viper.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viper.account.model.Account;
import com.viper.account.service.AccountService;
import com.viper.common.dto.ApiResponse;
import com.viper.common.enums.ResponseStatus;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	private AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Account>> addAccount(@RequestBody Account account){
		account = accountService.addAccount(account);
		
		ApiResponse<Account> response = new ApiResponse<>(
					ResponseStatus.SUCCESS,
					"Account created successfully",
					account
				);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/all")
	public ResponseEntity<ApiResponse<List<Account>>> addAllAccount(@RequestBody List<Account> accountList){
		accountList = accountService.addAllAccount(accountList);
		
		ApiResponse<List<Account>> response = new ApiResponse<>(
					ResponseStatus.SUCCESS,
					"All accounts created successfully",
					accountList
				);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{accountNo}")
	public ResponseEntity<ApiResponse<Account>> getAccount(@PathVariable String accountNo){
		Account account = accountService.getAccount(accountNo);
		
		ApiResponse<Account> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"Account fetched successfully",
				account
			);
	
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<Account>>> getAllAccounts(){
		List<Account> list = accountService.getAllAccounts();
		
		ApiResponse<List<Account>> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"All accounts fetched successfully",
				list
			);
	
	return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/{accountNo}")
	public ResponseEntity<ApiResponse<Account>> updateAccount(@PathVariable String accountNo, @RequestBody Account account){
		account = accountService.updateAccount(accountNo, account);
		
		ApiResponse<Account> response = new ApiResponse<>(
					ResponseStatus.SUCCESS,
					"Account updated successfully",
					account
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{accountNo}")
	public ResponseEntity<ApiResponse<Account>> deleteAccount(@PathVariable String accountNo){
		accountService.deleteAccount(accountNo);
		
		ApiResponse<Account> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"Account deleted successfully",
				null
			);
	
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
