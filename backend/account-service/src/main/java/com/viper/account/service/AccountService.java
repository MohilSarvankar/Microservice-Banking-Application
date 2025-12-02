package com.viper.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viper.account.client.CustomerClient;
import com.viper.account.model.Account;
import com.viper.account.model.CustomerDto;
import com.viper.account.repo.AccountRepo;
import com.viper.common.dto.ApiResponse;
import com.viper.common.enums.ResponseStatus;
import com.viper.common.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
	
	private AccountRepo accountRepo;
	private CustomerClient customerClient;
	
	public AccountService(AccountRepo accountRepo, CustomerClient customerClient) {
		this.accountRepo = accountRepo;
		this.customerClient = customerClient;
	}

	public Account addAccount(Account account) {
		validateCustomer(account);
		return accountRepo.save(account);
	}
	
	public List<Account> addAllAccount(List<Account> accountList) {
		accountList.forEach(account -> validateCustomer(account));
		return accountRepo.saveAll(accountList);
	}

	public List<Account> getAllAccounts() {
		return accountRepo.findAll();
	}

	public Account getAccount(String accountNo) {
		return accountRepo
				.findByAccountNo(accountNo)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));
	}

	public Account updateAccount(String accountNo, Account account) {
		validateCustomer(account);
		Account existing = getAccount(accountNo);
		
		existing.setCustomerId(account.getCustomerId());
		existing.setBranch(account.getBranch());
		existing.setAccountType(account.getAccountType());
		existing.setBalance(account.getBalance());
		
		return accountRepo.save(existing);
	}
	
	@Transactional
	public void deleteAccount(String accountNo) {
		accountRepo.deleteByAccountNo(accountNo);
	}
	
	private void validateCustomer(Account account) {
		ApiResponse<CustomerDto> response = customerClient.getCustomer(account.getCustomerId()).getBody();
		if(response.getStatus() == ResponseStatus.ERROR)
			throw new ResourceNotFoundException("Customer not found");
	}
}
