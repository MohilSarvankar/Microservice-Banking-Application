package com.viper.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viper.customer.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
