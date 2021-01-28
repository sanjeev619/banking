package com.bank.web.customerAccount.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.web.customer.entity.Customer;
import com.bank.web.customerAccount.entity.CustomerAccountMap;

public interface CustomerAccountMapRepository extends JpaRepository<CustomerAccountMap, Long>{

	List<Customer> findByIsActiveTrue();

	Optional<CustomerAccountMap> findByCustomerIdAndAccountIdAndIsActiveTrue(long customerId, long accountId);

	@Query("SELECT id from CustomerAccountMap WHERE accountId = ?1 and isActive = true")
	List<Long> findCustomerIdByAccountIdAndIsActiveTrue(long accountId);

//	@Query("select new com.bank.web.customer.entity.Customer(c) from CustomerAccountMap cam "
//			+ "inner join com.bank.web.customer.entity.Customer c on cam.customerId = c.id "
//			+ "WHERE cam.accountId = ?1 and cam.isActive=true")
//	List<Customer> findByAccountIdAndIsActiveTrue(long accountId);

}
