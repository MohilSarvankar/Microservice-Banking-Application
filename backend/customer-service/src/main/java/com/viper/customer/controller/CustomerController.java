package com.viper.customer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viper.customer.enums.ResponseStatus;
import com.viper.customer.model.ApiResponse;
import com.viper.customer.model.Customer;
import com.viper.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers(){
		List<Customer> list = customerService.getAllCustomers();
		
		ApiResponse<List<Customer>> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"Customers fetched successfully",
				list
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Customer>> getCustomer(@PathVariable long id){
		Customer customer = customerService.getCustomer(id);
		
		ApiResponse<Customer> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"Customer fetched successfully",
				customer
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Customer>> addCustomer(@RequestBody Customer customer){
		customer = customerService.addCustomer(customer);
		
		ApiResponse<Customer> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"Customer created successfully",
				customer
				);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/all")
	public ResponseEntity<ApiResponse<List<Customer>>> addAllCustomers(@RequestBody List<Customer> custList){
		custList = customerService.addAllCustomers(custList);
		
		ApiResponse<List<Customer>> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"All customers created successfully",
				custList
				);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Customer>> updateCustomer(@PathVariable long id, @RequestBody Customer customer){
		customer = customerService.updateCustomer(customer);
		
		ApiResponse<Customer> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"Customer updated successfully",
				customer
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Customer>> deleteCustomer(@PathVariable long id){
		customerService.deleteCustomer(id);
		
		ApiResponse<Customer> response = new ApiResponse<>(
				ResponseStatus.SUCCESS,
				"Customer deleted successfully",
				null
				);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	} 
}
