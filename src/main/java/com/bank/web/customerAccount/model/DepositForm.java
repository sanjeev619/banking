package com.bank.web.customerAccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositForm {
	private double amount;
	private long toAccountId;
	private String remark;
}
