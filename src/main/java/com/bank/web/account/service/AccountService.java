package com.bank.web.account.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bank.web.account.entity.Account;
import com.bank.web.account.model.AccountForm;
import com.bank.web.account.model.AccountStatement;
import com.bank.web.account.model.TransactionEvent;
import com.bank.web.account.repository.AccountRepository;
import com.bank.web.customerAccount.entity.TransactionHistory;
import com.bank.web.customerAccount.repository.CustomerAccountMapRepository;
import com.bank.web.customerAccount.repository.TransactionHistoryRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerAccountMapRepository customerAccountMapRepository;
	
	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;
	
	public List<Account> getAllAccounts() {
		return accountRepository.findByIsActiveTrue();
	}

	public Account getAccount(long accountId) {
		Optional<Account> optionalAccount = accountRepository.findById(accountId);
		if(! optionalAccount.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Account Not Found For the given Id "+accountId);
		}
		return optionalAccount.get();
	}

	@Transactional
	public Account addAccount(AccountForm accountForm) {
		Account account = Account.from(accountForm);
		account = accountRepository.save(account);
		return account;
	}

	public Double getAccountBalance(long accountId) {
		Account account = getAccount(accountId);
		return account.getBalance();
	}

	public AccountStatement getAccountStatement(long accountId) {
		Account account = getAccount(accountId);
		populateAccountWithCustomers(account);
		
		List<TransactionHistory> transactionsHistory = transactionHistoryRepository.findByAccountId(accountId);
		List<TransactionEvent> transactionEvents = transactionsHistory.stream().map(transaction -> {
			TransactionEvent transactionEvent = TransactionEvent.from(accountId, transaction);
			return transactionEvent;
		}).collect(Collectors.toList());
		
		return new AccountStatement(account, transactionEvents);
	}

	private void populateAccountWithCustomers(Account account) {
		account.setCustomers(customerAccountMapRepository.findByAccountIdAndIsActiveTrue(account.getId()));
	}

}
