package com.viper.transaction.model;

import java.time.LocalDateTime;

import com.viper.transaction.enums.DebitCreditFlag;
import com.viper.transaction.enums.TransactionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(
		name = "transactions", 
		indexes = {
				@Index(name="idx_account_no", columnList = "accountNo"),
				@Index(name="idx_transaction_ref", columnList = "transactionRef")
			}
		)
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String transactionRef;
	
	private String accountNo;
	
	private Long amount;
	
	@Enumerated(EnumType.STRING)
	private DebitCreditFlag flag;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	
	private String remarks;
	
	private String failureReason;
	
	private LocalDateTime transactionStartTime;
	
	private LocalDateTime transactionEndTime;
	
	@PrePersist
	public void prePersist() {
		this.transactionStartTime = LocalDateTime.now();
		if(this.status == null) {
			this.status = TransactionStatus.PENDING;
		}
	}
	
	@PreUpdate
	public void preUpdate() {
		if(this.status == TransactionStatus.SUCCESS || this.status == TransactionStatus.FAILED) {
			this.transactionEndTime = LocalDateTime.now();
		}
	}
}
