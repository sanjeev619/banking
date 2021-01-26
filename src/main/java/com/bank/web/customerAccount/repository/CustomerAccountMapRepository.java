package com.bank.web.customerAccount.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.web.customer.entity.Customer;
import com.bank.web.customerAccount.entity.CustomerAccountMap;

public interface CustomerAccountMapRepository extends JpaRepository<CustomerAccountMap, Long>{

	List<Customer> findByIsActiveTrue();

	Optional<CustomerAccountMap> findByCustomerIdAndAccountIdAndIsActiveTrue(long customerId, long accountId);

	List<Customer> findByAccountIdAndIsActiveTrue(long accountId);

}
