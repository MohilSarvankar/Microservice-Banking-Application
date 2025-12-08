package com.viper.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class TransactionSequence {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "txn_ref_seq")
	@SequenceGenerator(name = "txn_ref_seq", sequenceName = "txn_ref_seq", allocationSize = 1)
	private Long id;
}
