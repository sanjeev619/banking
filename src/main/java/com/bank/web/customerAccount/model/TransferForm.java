package com.bank.web.customerAccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferForm {
	private double amount;
	private long fromAccountId;
	private long toAccountId;
	private String remark;
}
