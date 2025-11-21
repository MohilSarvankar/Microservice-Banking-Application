package com.viper.account.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viper.account.enums.AccountType;

import jakarta.persistence.Column;
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
		name = "account",
		indexes = {
				@Index(name = "idx_account_no", columnList = "accountNo")
				}
		)
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonIgnore
	private long internalId;
	
	@Column(unique = true, nullable = false)
	private String accountNo;
	
	private int branch;
	private long customerId;
	private long balance;
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.accountNo = String.format("%03d", this.branch) 
						+ String.format("%02d",this.accountType.ordinal()+1) 
						+ String.format("%010d", this.internalId);
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
}
