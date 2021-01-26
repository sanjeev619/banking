package com.bank.web.customerAccount.entity;

import javax.persistence.Entity;

import com.bank.web.base.AuditableEntity;
import com.bank.web.customerAccount.model.CustomerAccountMapForm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class CustomerAccountMap extends AuditableEntity<Long>{
	
	private long customerId;
	private long accountId;
	private Boolean isActive;

	public static CustomerAccountMap from(CustomerAccountMapForm customerAccountForm) {
		CustomerAccountMap customerAccount = new CustomerAccountMap();
		customerAccount.customerId = customerAccountForm.getCustomerId();
		customerAccount.accountId = customerAccountForm.getAccountId();
		customerAccount.isActive = true;
		return customerAccount;
	}

}
