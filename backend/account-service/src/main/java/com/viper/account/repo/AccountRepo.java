package com.viper.account.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viper.account.model.Account;

import jakarta.persistence.LockModeType;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long>{
	
	public Optional<Account> findByAccountNo(String accountNo);
	
	public void deleteByAccountNo(String accountNo);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from Account a where a.accountNo = :accountNo")
	public Optional<Account> findAccountForUpdate(String accountNo);
}
