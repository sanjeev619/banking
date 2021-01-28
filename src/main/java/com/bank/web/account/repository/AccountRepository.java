package com.bank.web.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.web.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	List<Account> findByIsActiveTrue();

	Optional<Account> findByAccountNo(String tenDigitNumber);

}
