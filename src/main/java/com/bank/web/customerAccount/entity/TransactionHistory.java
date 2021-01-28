package com.bank.web.customerAccount.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bank.web.account.entity.Account;
import com.bank.web.base.AuditableEntity;
import com.bank.web.customerAccount.model.DepositForm;
import com.bank.web.customerAccount.model.TransferForm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(indexes = @Index(columnList = "fromAccountId, toAccountId"))
public class TransactionHistory extends AuditableEntity<Long>{
	
	public enum TransactionStatus{
		INITIALIZED, SUCCESS, FAILED
	}
	
	private long fromAccountId;
	private long toAccountId;
	private double amount;
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	private String remark;
	private boolean isActive;
	
	@Transient
	private Account fromAccount;
	@Transient
	private Account toAccount;
	
	public static TransactionHistory from(TransferForm transferForm) {
		TransactionHistory transaction = new TransactionHistory();
		transaction.toAccountId = transferForm.getToAccountId();
		transaction.fromAccountId = transferForm.getFromAccountId();
		transaction.amount = transferForm.getAmount();
		transaction.status = TransactionStatus.INITIALIZED;
		transaction.remark = transferForm.getRemark();
		transaction.isActive = true;
		return transaction;
	}

	public static TransactionHistory from(DepositForm depositForm) {
		TransactionHistory transaction = new TransactionHistory();
		transaction.toAccountId = depositForm.getToAccountId();
		transaction.amount = depositForm.getAmount();
		transaction.status = TransactionStatus.INITIALIZED;
		transaction.remark = depositForm.getRemark();
		transaction.isActive = true;
		return transaction;
	}
}
