package com.bank.web.customerAccount.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bank.web.account.entity.Account;
import com.bank.web.account.repository.AccountRepository;
import com.bank.web.account.service.AccountService;
import com.bank.web.customer.entity.Customer;
import com.bank.web.customer.service.CustomerService;
import com.bank.web.customerAccount.entity.CustomerAccountMap;
import com.bank.web.customerAccount.entity.TransactionHistory;
import com.bank.web.customerAccount.entity.TransactionHistory.TransactionStatus;
import com.bank.web.customerAccount.model.CustomerAccountMapForm;
import com.bank.web.customerAccount.model.DepositForm;
import com.bank.web.customerAccount.model.TransferForm;
import com.bank.web.customerAccount.repository.CustomerAccountMapRepository;
import com.bank.web.customerAccount.repository.TransactionHistoryRepository;

@Service
public class BankingService {

	@Autowired
	private CustomerAccountMapRepository customerAccountMapRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountService accountService;
	
	public CustomerAccountMap linkAccountToCustomer(CustomerAccountMapForm form) {
		long customerId = form.getCustomerId();
		long accountId = form.getAccountId();
		
		Customer customer = customerService.getCustomer(customerId);
		Account account = accountService.getActiveAccount(accountId);
		
		Optional<CustomerAccountMap> optionalMap = customerAccountMapRepository.findByCustomerIdAndAccountIdAndIsActiveTrue(customerId, accountId);
		if(optionalMap.isPresent() &&  BooleanUtils.isTrue(optionalMap.get().getIsActive())) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Account is already linked");
		}
		
		CustomerAccountMap customerAccountMap = CustomerAccountMap.from(form);
		return customerAccountMapRepository.save(customerAccountMap);
	}

	@Transactional
	public boolean transfer(TransferForm transferForm) {
		double amount = transferForm.getAmount();

		long fromAccountId = transferForm.getFromAccountId();
		long toAccountId = transferForm.getToAccountId();
		if(fromAccountId <= 0 || toAccountId <= 0 || fromAccountId == toAccountId) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid transaction");
		}

		Account fromAccount = accountRepository
				.findById(transferForm.getFromAccountId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Source Account doesn't exist"));
		Account toAccount = accountRepository
				.findById(transferForm.getToAccountId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Destination Account doesn't exist"));
		
		//TODO validate
		if(! fromAccount.hasEnoughBalance(amount)) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not enough balance");
		}
		
		TransactionHistory transaction = TransactionHistory.from(transferForm);
		transaction = transactionHistoryRepository.save(transaction);
		
		fromAccount.debitBalance(amount);
		toAccount.creditBalance(amount);
		
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		
		transaction.setStatus(TransactionStatus.SUCCESS);
		transactionHistoryRepository.save(transaction);
		return true;
	}

	public boolean depositMoney(DepositForm depositForm) {
		Account toAccount = accountRepository
				.findById(depositForm.getToAccountId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Destination Account doesn't exist"));
		
		TransactionHistory transaction = TransactionHistory.from(depositForm);
		transaction = transactionHistoryRepository.save(transaction);
		
		double amount = depositForm.getAmount();
		toAccount.creditBalance(amount);
		accountRepository.save(toAccount);
		
		transaction.setStatus(TransactionStatus.SUCCESS);
		transactionHistoryRepository.save(transaction);
		return true;
	}

	

}
