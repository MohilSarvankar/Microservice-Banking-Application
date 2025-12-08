package com.viper.transaction.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viper.common.dto.ApiResponse;
import com.viper.common.enums.ResponseStatus;
import com.viper.transaction.client.AccountClient;
import com.viper.transaction.enums.DebitCreditFlag;
import com.viper.transaction.enums.TransactionStatus;
import com.viper.transaction.dto.FundTransferRequestDto;
import com.viper.transaction.model.Transaction;
import com.viper.transaction.model.TransactionSequence;
import com.viper.transaction.dto.TransactionRequestDto;
import com.viper.transaction.dto.TransactionResponseDto;
import com.viper.transaction.repo.TransactionRepo;
import com.viper.transaction.repo.TransactionSequenceRepo;

import feign.FeignException;

@Service
public class TransactionService {
	
	private TransactionRepo txnRepo;
	private AccountClient accountClient;
	private TransactionSequenceRepo txnSeqRepo;
	
	public TransactionService(TransactionRepo txnRepo, AccountClient accountClient, TransactionSequenceRepo txnSeqRepo) {
		this.txnRepo = txnRepo;
		this.accountClient = accountClient;
		this.txnSeqRepo = txnSeqRepo;
	}

	public TransactionResponseDto transferFunds(TransactionRequestDto txnRequest) {

		Long txnSeq = txnSeqRepo.save(new TransactionSequence()).getId();
		String txnRefNo = "TXN" + String.format("%010d", txnSeq);

		Transaction debitTxn = new Transaction();
		debitTxn.setTransactionRef(txnRefNo);
		debitTxn.setAccountNo(txnRequest.getFromAccount());
		debitTxn.setAmount(txnRequest.getAmount());
		debitTxn.setRemarks(txnRequest.getRemarks());
		debitTxn.setFlag(DebitCreditFlag.DEBIT);
		
		Transaction creditTxn = new Transaction();
		creditTxn.setTransactionRef(txnRefNo);
		creditTxn.setAccountNo(txnRequest.getToAccount());
		creditTxn.setAmount(txnRequest.getAmount());
		creditTxn.setRemarks(txnRequest.getRemarks());
		creditTxn.setFlag(DebitCreditFlag.CREDIT);
		
		debitTxn = txnRepo.save(debitTxn);
		creditTxn  = txnRepo.save(creditTxn);
		
		TransactionResponseDto txnResponse = new TransactionResponseDto();
		txnResponse.setFromAccount(txnRequest.getFromAccount());
		txnResponse.setToAccount(txnRequest.getToAccount());
		txnResponse.setAmount(txnRequest.getAmount());
		txnResponse.setTransactionRef(txnRefNo);
		
		ApiResponse<?> response = null;
		
		try {
			FundTransferRequestDto request = new FundTransferRequestDto(txnRequest.getFromAccount(), txnRequest.getToAccount(), txnRequest.getAmount());
			response = accountClient.transferFunds(request).getBody();
		}
		catch(FeignException e) {
			String errorBody = e.contentUTF8();
			try {
				response = new ObjectMapper().readValue(errorBody,ApiResponse.class);
			} 
			catch (JsonProcessingException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
		}
		
		if(response.getStatus() == ResponseStatus.SUCCESS) {
			txnResponse.setStatus(TransactionStatus.SUCCESS);
			
			debitTxn.setStatus(TransactionStatus.SUCCESS);
			creditTxn.setStatus(TransactionStatus.SUCCESS);
		}
		else {
			txnResponse.setStatus(TransactionStatus.FAILED);
			txnResponse.setFailureReason(response.getMessage());
			
			debitTxn.setStatus(TransactionStatus.FAILED);
			debitTxn.setFailureReason(response.getMessage());
			creditTxn.setStatus(TransactionStatus.FAILED);
			creditTxn.setFailureReason(response.getMessage());
		}
		
		debitTxn = txnRepo.save(debitTxn);
		creditTxn  = txnRepo.save(creditTxn);

		return txnResponse;
	}

}
