package com.bank.web.account.model;

import java.util.List;

import com.bank.web.account.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatement {
	private Account account;
	private List<TransactionEvent> transactionEvents;
}
