package com.bank.web.account.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		transactionEvent.transactionDate = dateFormatter.format(transaction.getUpdatedOn());
		
		String remark = transaction.getRemark();
		if(transaction.getFromAccountId() == accountId) {
			transactionEvent.withdrawalAmount = transaction.getAmount();
			if(transaction.getToAccount() != null) remark += " - "+transaction.getToAccount().getAccountNo();
		} else if(transaction.getFromAccountId() > 0){
			transactionEvent.depositAmount = transaction.getAmount();
			if(transaction.getFromAccount() != null) remark += " - "+transaction.getFromAccount().getAccountNo();
		} else {
			transactionEvent.depositAmount = transaction.getAmount();
			remark += " - deposited";
		}
		transactionEvent.remark = remark;
		return transactionEvent;
		
	}
	
}
