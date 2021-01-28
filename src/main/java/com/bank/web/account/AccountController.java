package com.bank.web.account;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.web.account.entity.Account;
import com.bank.web.account.model.AccountForm;
import com.bank.web.account.model.AccountStatement;
import com.bank.web.account.service.AccountService;
import com.lowagie.text.DocumentException;

@RequestMapping("api")
@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("account/unique-number")
	public String getAvailableAccountNumber() {
		return accountService.getAvailableAccountNumber();
	}
	
	@GetMapping("account")
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}
	
	@GetMapping("account/{accountId}")
	public Account getAccount(@PathVariable long accountId) {
		return accountService.getActiveAccount(accountId);
	}
	
	@PostMapping("account")
	public Account addAccount(@RequestBody AccountForm accountForm) {
		return accountService.addAccount(accountForm);
	}

	@GetMapping("account/{accountId}/balance")
	public double getAccountBalance(@PathVariable long accountId) {
		return accountService.getAccountBalance(accountId);
	}
	
	@GetMapping("account/{accountId}/statement")
	public AccountStatement getAccountStatement(@PathVariable long accountId, 
			@RequestParam(defaultValue = "#{new java.util.Date(0L)}") Date startDate,
			@RequestParam(defaultValue = "#{new java.util.Date()}") Date endDate) {
		return accountService.getAccountStatement(accountId, startDate, endDate);
	}
	
	@GetMapping("account/{accountId}/statement/export/pdf")
	public void getAccountStatementAsPdf(@PathVariable long accountId, HttpServletResponse response, 
			@RequestParam(defaultValue = "#{new java.util.Date(0L)}") Date startDate,
			@RequestParam(defaultValue = "#{new java.util.Date()}") Date endDate) throws DocumentException, IOException {
		accountService.getAccountStatementAsPdf(accountId, startDate, endDate, response);
	}
}
