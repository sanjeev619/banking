package com.bank.web.customer.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bank.web.customer.entity.Customer;
import com.bank.web.customer.model.CustomerForm;
import com.bank.web.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findByIsActiveTrue();
	}
	
	public Customer getCustomerById(long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if(! optionalCustomer.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Customer Not Found For the given Id "+customerId);
		}
		return optionalCustomer.get();
	}

	public Customer getCustomer(long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findByIdAndIsActiveTrue(customerId);
		if(! optionalCustomer.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Customer Not Found For the given Id "+customerId);
		}
		return optionalCustomer.get();
	}

	@Transactional
	public Customer addCustomer(CustomerForm customerForm) {
		Customer customer = Customer.from(customerForm);
		customer = customerRepository.save(customer);
		return customer;
	}

	@Transactional
	public Customer updateCustomer(long customerId, CustomerForm customerForm) {
		Customer customer = getCustomerById(customerId);
		customer.update(customerForm);
		return customerRepository.save(customer);
	}

	@Transactional
	public Customer deleteCustomer(long customerId) {
		System.out.println("customerId "+customerId);
		Customer customer = getCustomer(customerId);
		if(! customer.isActive()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Customer is already deleted with Id "+customerId);
		}
		customer.setActive(false);
		return customerRepository.save(customer);
	}


}
