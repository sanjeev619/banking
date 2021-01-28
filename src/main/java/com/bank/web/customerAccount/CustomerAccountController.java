package com.bank.web.customerAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.web.customerAccount.entity.CustomerAccountMap;
import com.bank.web.customerAccount.model.CustomerAccountMapForm;
import com.bank.web.customerAccount.model.DepositForm;
import com.bank.web.customerAccount.model.TransferForm;
import com.bank.web.customerAccount.service.BankingService;

@RequestMapping("api")
@RestController
public class CustomerAccountController {
	
	@Autowired
	private BankingService bankingService;
	
	@PostMapping("customer/link-account")
	public CustomerAccountMap linkAccountToCustomer(@RequestBody CustomerAccountMapForm form) {
		return bankingService.linkAccountToCustomer(form);
	}
	
	@PostMapping("transfer")
	public boolean transfer(@RequestBody TransferForm transferForm) {
		return bankingService.transfer(transferForm);
	}
	
	@PostMapping("deposit")
	public boolean depositMoney(@RequestBody DepositForm depositForm) {
		return bankingService.depositMoney(depositForm);
	}
}
