package com.bank.web.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.web.auth.repository.SecurityUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private SecurityUserRepository securityUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return securityUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
