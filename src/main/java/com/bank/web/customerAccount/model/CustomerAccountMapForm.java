package com.bank.web.customerAccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerAccountMapForm {
	private long customerId;
	private long accountId;
}
