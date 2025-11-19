package com.viper.customer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.viper.customer.exception.ResourceNotFoundException;
import com.viper.customer.model.Customer;
import com.viper.customer.repo.CustomerRepo;

@Service
public class CustomerService {
	
	private CustomerRepo customerRepo;
	
	public CustomerService(CustomerRepo customerRepo) {
		this.customerRepo = customerRepo;
	}

	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	public Customer getCustomer(long id) {
		return customerRepo
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	}

	public Customer addCustomer(Customer customer) {
		return customerRepo.save(customer);
	}
	
	public List<Customer> addAllCustomers(List<Customer> custList) {
		return customerRepo.saveAll(custList);
	}

	public Customer updateCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	public void deleteCustomer(long id) {
		customerRepo.deleteById(id);
	}
	
}
