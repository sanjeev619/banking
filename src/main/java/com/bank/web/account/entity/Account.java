package com.bank.web.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bank.web.account.model.AccountForm;
import com.bank.web.base.AuditableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class Account extends AuditableEntity<Long>{
	
	public enum AccountType {
		SAVINGS, CURRENT, LOAN, SALARY
	}
	
	@Column(unique = true)
	private String accountNo;
	private double balance;
	private String branchCode;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	private boolean isActive;
	
	
	public static Account from(AccountForm accountForm) {
		Account account = new Account();
		account.accountType = accountForm.getAccountType();
		account.update(accountForm);
		account.isActive = true;
		return account;
	}

	public void update(AccountForm accountForm) {
		this.branchCode = accountForm.getBranchCode();
	}

	public void debitBalance(double amount) {
		balance -= amount;
	}

	public void creditBalance(double amount) {
		balance += amount;
	}

	public boolean hasEnoughBalance(double amount) {
		return this.balance >= amount;
	}
	
	
}
