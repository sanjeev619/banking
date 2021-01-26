package com.bank.web.auth.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bank.web.auth.config.MyUserDetailsService;
import com.bank.web.auth.config.util.JwUtil;
import com.bank.web.auth.entity.SecurityUser;
import com.bank.web.auth.entity.SecurityUser.GlobalRole;
import com.bank.web.auth.entity.TokenWithExpiry;
import com.bank.web.auth.model.AuthenticationRequest;
import com.bank.web.auth.model.AuthenticationResponse;
import com.bank.web.auth.model.PasswordForm;
import com.bank.web.auth.repository.SecurityUserRepository;
import com.bank.web.auth.repository.TokenWithExpiryRepository;
import com.bank.web.employee.entity.Employee;

@Service
public class AuthService {
	
	private static String SET_PASSWORD_URL = "http://localhost/public/set-password/%s"; 
	
	@Autowired
	private SecurityUserRepository securityUserRepository;

	@Autowired
	private TokenWithExpiryRepository tokenWithExpiryRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwUtil jwtUtil;

	public SecurityUser addUser(Employee employee, GlobalRole userRole) {
		Optional<SecurityUser> optionalUser = Optional.empty();
		if(employee.getSecurityUserId() > 0) {
			optionalUser = securityUserRepository.findById(employee.getSecurityUserId());
		}
		if(! optionalUser.isPresent())
			optionalUser = Optional.of(SecurityUser.from(employee, userRole));
		
		SecurityUser securityUser = optionalUser.get();
		securityUser.setEnabled(true);
		securityUser = securityUserRepository.save(securityUser);
		
		generateToken(securityUser);
		return securityUser;
	}

	private TokenWithExpiry generateToken(SecurityUser securityUser) {
		long userId = securityUser.getId();
		Optional<TokenWithExpiry> optionalToken = tokenWithExpiryRepository.findByUserIdAndIsActive(userId, true);
		if(optionalToken.isPresent()) {
			return optionalToken.get();
		}
		TokenWithExpiry tokenWithExpiry = new TokenWithExpiry(userId);
		System.out.println("Set password url - "+String.format(SET_PASSWORD_URL, tokenWithExpiry.getToken()));
		//TODO send a mail to activate the account
		
		return tokenWithExpiryRepository.save(tokenWithExpiry);
	}

	public boolean disableUser(long securityUserId) {
		return securityUserRepository.disableUser(securityUserId) > 0;
	}
	
	private TokenWithExpiry getTokenWithExpiry(String token) {
		Optional<TokenWithExpiry> optionalToken = tokenWithExpiryRepository.findByToken(token);
		if(! optionalToken.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid Url");
		}
		TokenWithExpiry tokenWithExpiry = optionalToken.get();
		if(! BooleanUtils.isTrue(tokenWithExpiry.getIsActive())) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Url is expired");
		}
		return tokenWithExpiry;
	}

	@Transactional
	public String setPassword(String token, PasswordForm passwordForm) {
		TokenWithExpiry tokenWithExpiry = getTokenWithExpiry(token);
		SecurityUser securityUser = securityUserRepository.findById(tokenWithExpiry.getUserId()).orElseThrow(() -> new RuntimeException("User nor found"));
		
		securityUser.setPassword(passwordForm.getPassword());
		securityUser.setEnabled(true);
		securityUser = securityUserRepository.save(securityUser);
		disablToken(tokenWithExpiry.getId());
		
		return "success";
	}
	
	public void disablToken(TokenWithExpiry tokenWithExpiry) {
		tokenWithExpiry.setIsActive(null);
		tokenWithExpiryRepository.save(tokenWithExpiry);
	}
	
	public boolean disablToken(long tokenId) {
		return tokenWithExpiryRepository.disableToken(tokenId) > 0;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest authenicationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenicationRequest.getUsername(), authenicationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenicationRequest.getUsername());
		String jwt = jwtUtil.generateToken(userDetails);
		return new AuthenticationResponse(jwt);
	}

	
}
