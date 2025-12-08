package com.viper.transaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viper.transaction.model.TransactionSequence;

@Repository
public interface TransactionSequenceRepo extends JpaRepository<TransactionSequence, Long> {

}
