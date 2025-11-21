package com.viper.account.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viper.account.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long>{
	
	public Optional<Account> findByAccountNo(String accountNo);
	
	public void deleteByAccountNo(String accountNo);
}
