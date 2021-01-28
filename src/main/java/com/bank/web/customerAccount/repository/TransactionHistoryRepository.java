package com.bank.web.customerAccount.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank.web.customerAccount.entity.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>{

	@Query("select th from TransactionHistory th WHERE (th.fromAccountId = ?1 or th.toAccountId = ?1) and th.updatedOn between ?2 and ?3 and th.isActive=true")
	List<TransactionHistory> findByAccountId(long accountId, Date startDate, Date endDate);

//	@Query("SELECT new com.bank.web.account.model.TransactionEvent(createdOn as transactionDate, remark as remark, "
//			+ "if(fromAccountId = ?1, amount, 0) as withdrawalAmount, "
//			+ "if(toAccountId = ?1, amount, 0) as depositAmount "
//			+ ") "
//			+ "FROM com.bank.web.customerAccount.entity.TransactionHistory WHERE (fromAccountId = ?1 or toAccountId = ?1) and isActive=true")
//	List<TransactionEvent> findTransactions(Long accountId);

}
