package com.bank.web.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.web.account.entity.Account;
import com.bank.web.account.model.AccountForm;
import com.bank.web.account.service.AccountService;

@RequestMapping("api")
@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("account")
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}
	
	@GetMapping("account/{accountId}")
	public Account getaccount(@PathVariable long accountId) {
		return accountService.getAccount(accountId);
	}
	
	@PostMapping("account")
	public Account addAccount(@RequestBody AccountForm accountForm) {
		return accountService.addAccount(accountForm);
	}

}
