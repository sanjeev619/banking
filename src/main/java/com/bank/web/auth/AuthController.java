package com.bank.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.web.auth.model.PasswordForm;
import com.bank.web.auth.service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("public/set-password/{token}")
	public String setPassword(@PathVariable String token, @RequestBody PasswordForm passwordForm) {
		return authService.setPassword(token, passwordForm);
	}
}
