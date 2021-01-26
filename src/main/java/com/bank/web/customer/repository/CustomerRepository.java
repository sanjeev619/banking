package com.bank.web.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.web.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	List<Customer> findByIsActiveTrue();

}
