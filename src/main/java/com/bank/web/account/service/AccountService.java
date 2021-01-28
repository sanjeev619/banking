package com.bank.web.account.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
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
import com.bank.web.customer.repository.CustomerRepository;
import com.bank.web.customerAccount.entity.TransactionHistory;
import com.bank.web.customerAccount.repository.CustomerAccountMapRepository;
import com.bank.web.customerAccount.repository.TransactionHistoryRepository;
import com.lowagie.text.DocumentException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerAccountMapRepository customerAccountMapRepository;
	
	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;
	
	public String getAvailableAccountNumber() {
		String tenDigitNumber = "";
		boolean unique = false;
		while(! unique) {
			tenDigitNumber = getRandomTenDigitNumber();
			Optional<Account> optionalAccount = accountRepository.findByAccountNo(tenDigitNumber);
			unique = ! optionalAccount.isPresent();
		}
		return tenDigitNumber;
	}
	
	public String getRandomTenDigitNumber() {
		long aStart = 1000000000L;
		long aEnd = 9999999999L;
	    long range = aEnd - (long)aStart + 1;
	    long fraction = (long)(range * new Random().nextDouble());
	    long randomNumber =  fraction + (long)aStart;    
	    System.out.println(randomNumber + " " + randomNumber);
	    return String.valueOf(randomNumber);
	}
	
	public List<Account> getAllAccounts() {
		return accountRepository.findByIsActiveTrue();
	}
	
	public Account getActiveAccount(long accountId) {
		Account account = getAccountById(accountId);
		if(! account.isActive()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Account is not active with id "+accountId);
		}
		return account;
	}

	public Account getAccountById(long accountId) {
		Optional<Account> optionalAccount = accountRepository.findById(accountId);
		if(! optionalAccount.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Account Not Found For the given Id "+accountId);
		}
		return optionalAccount.get();
	}

	@Transactional
	public Account addAccount(AccountForm accountForm) {
		Optional<Account> optionalAccount = accountRepository.findByAccountNo(accountForm.getAccountNo());
		if(optionalAccount.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The provided account Number is already used "+accountForm.getAccountNo());
		}
		Account account = Account.from(accountForm);
		account = accountRepository.save(account);
		return account;
	}

	public double getAccountBalance(long accountId) {
		Account account = getAccountById(accountId);
		return account.getBalance();
	}

	public AccountStatement getAccountStatement(long accountId, Date startDate, Date endDate) {
		Account account = getActiveAccount(accountId);
		populateAccountWithCustomers(account);
		
		List<TransactionHistory> transactionsHistory = transactionHistoryRepository.findByAccountId(accountId, startDate, endDate);
		populateTransactionHistoryWithAccounts(transactionsHistory);
		List<TransactionEvent> transactionEvents = transactionsHistory.stream().map(transaction -> {
			TransactionEvent transactionEvent = TransactionEvent.from(accountId, transaction);
			return transactionEvent;
		}).collect(Collectors.toList());
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String dateRage = String.format("%s to %s", dateFormatter.format(startDate), dateFormatter.format(endDate));
		return new AccountStatement(dateRage, transactionEvents, account);
	}

	private void populateTransactionHistoryWithAccounts(List<TransactionHistory> transactionsHistory) {
		Set<Long> accountIds = transactionsHistory.stream().map(th -> Arrays.asList(th.getFromAccountId(), th.getToAccountId()))
				.flatMap(list -> list.stream()).collect(Collectors.toSet());
		Map<Long, Account> accountMap = accountRepository.findAllById(accountIds).stream().collect(Collectors.toMap(Account::getId, Function.identity()));
		transactionsHistory.stream().forEach(th -> {
			th.setFromAccount(accountMap.get(th.getFromAccountId()));
			th.setToAccount(accountMap.get(th.getToAccountId()));
		});
	}

	private void populateAccountWithCustomers(Account account) {
		List<Long> customerIds = customerAccountMapRepository.findCustomerIdByAccountIdAndIsActiveTrue(account.getId());
		account.setCustomers(customerRepository.findAllById(customerIds));
	}

	public void getAccountStatementAsPdf(long accountId, Date startDate, Date endDate, HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=statement" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        AccountStatement accountStatement = getAccountStatement(accountId, startDate, endDate);
         
        StatementPDFExporter exporter = new StatementPDFExporter(accountStatement);
        exporter.export(response);
	}

}
