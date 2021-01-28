package com.bank.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.web.auth.model.AuthenticationRequest;
import com.bank.web.auth.model.AuthenticationResponse;
import com.bank.web.auth.model.PasswordForm;
import com.bank.web.auth.service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("public/create-default-user")
	public String createDefaultUser() {
		return authService.createDefaultUser();
	}
	
	@PostMapping("authenticate")
	public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenicationRequest) throws Exception {
		return authService.authenticate(authenicationRequest);
	}
	
	@PostMapping("public/set-password/{token}")
	public String setPassword(@PathVariable String token, @RequestBody PasswordForm passwordForm) {
		return authService.setPassword(token, passwordForm);
	}
}
