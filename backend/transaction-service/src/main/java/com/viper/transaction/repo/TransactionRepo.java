package com.viper.transaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viper.transaction.model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
