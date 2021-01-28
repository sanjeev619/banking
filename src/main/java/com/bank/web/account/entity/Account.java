package com.bank.web.account.entity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.bank.web.account.model.AccountForm;
import com.bank.web.base.AuditableEntity;
import com.bank.web.customer.entity.Customer;

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
	
	@Transient
	private List<Customer> customers;
	
	
	public static Account from(AccountForm accountForm) {
		Account account = new Account();
		account.accountType = accountForm.getAccountType();
		account.accountNo = accountForm.getAccountNo();
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

	public String getCustomerNames() {
		if(customers == null)
			return "";
		return customers.stream().map(customer -> customer.getFullName()).collect(Collectors.joining(", "));
	}
	
	
}
