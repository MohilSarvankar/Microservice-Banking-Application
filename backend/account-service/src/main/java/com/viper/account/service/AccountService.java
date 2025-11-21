package com.viper.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.viper.account.exception.ResourceNotFoundException;
import com.viper.account.model.Account;
import com.viper.account.repo.AccountRepo;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
	
	private AccountRepo accountRepo;
	
	public AccountService(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}

	public Account addAccount(Account account) {
		return accountRepo.save(account);
	}
	
	public List<Account> addAllAccount(List<Account> accountList) {
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
	
	

}
