package com.bank.web.customerAccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.web.customerAccount.entity.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>{

}
