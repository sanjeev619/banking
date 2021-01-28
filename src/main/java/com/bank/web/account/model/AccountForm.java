package com.bank.web.account.model;

import com.bank.web.account.entity.Account.AccountType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountForm {
	private String accountNo;
	private String branchCode;
	private AccountType accountType = AccountType.SAVINGS;
}
