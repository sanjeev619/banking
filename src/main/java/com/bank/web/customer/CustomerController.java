package com.bank.web.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.web.customer.entity.Customer;
import com.bank.web.customer.model.CustomerForm;
import com.bank.web.customer.service.CustomerService;

@RequestMapping("api")
@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("customer")
	public List<Customer> getAllcustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("customer/{customerId}")
	public Customer getcustomer(@PathVariable long customerId) {
		return customerService.getCustomer(customerId);
	}
	
	@PostMapping("customer")
	public Customer addcustomer(@RequestBody CustomerForm customerForm) {
		return customerService.addCustomer(customerForm);
	}
	
	@PostMapping("customer/{customerId}")
	public Customer updatecustomer(@PathVariable long customerId, @RequestBody CustomerForm customerForm) {
		return customerService.updateCustomer(customerId, customerForm);
	}
	
	@PostMapping("customer/{customerId}/delete")
	public Customer deleteCustomer(@PathVariable long customerId) {
		return customerService.deleteCustomer(customerId);
	}
}
