package com.bank.web.account.model;

import com.bank.web.customerAccount.entity.TransactionHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {
	private String transactionDate;
	private String remark;
	private double withdrawalAmount;
	private double depositAmount;
	
	
	public static TransactionEvent from(long accountId, TransactionHistory transaction) {
		TransactionEvent transactionEvent = new TransactionEvent();
		transactionEvent.transactionDate = transaction.getUpdatedOn().toString();
		if(transaction.getFromAccountId() == accountId) {
			transactionEvent.withdrawalAmount = transaction.getAmount();
		} else {
			transactionEvent.depositAmount = transaction.getAmount();
		}
		transactionEvent.remark = transaction.getRemark();
		return transactionEvent;
	}
	
}
