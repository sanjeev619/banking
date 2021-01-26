package com.bank.web.account.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bank.web.account.entity.Account;
import com.bank.web.account.model.AccountForm;
import com.bank.web.account.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
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

}
