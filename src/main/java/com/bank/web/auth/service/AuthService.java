package com.bank.web.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.web.auth.entity.SecurityUser;
import com.bank.web.auth.entity.SecurityUser.GlobalRole;
import com.bank.web.auth.repository.SecurityUserRepository;
import com.bank.web.employee.entity.Employee;

@Service
public class AuthService {
	
	@Autowired
	private SecurityUserRepository securityUserRepository;

	public SecurityUser addUser(Employee employee, GlobalRole userRole) {
		Optional<SecurityUser> optionalUser = Optional.empty();
		if(employee.getSecurityUserId() > 0) {
			optionalUser = securityUserRepository.findById(employee.getSecurityUserId());
		}
		System.out.println(employee.getId());
		if(! optionalUser.isPresent())
			optionalUser = Optional.of(SecurityUser.from(employee, userRole));
		SecurityUser securityUser = optionalUser.get();
		securityUser.setDisabled(false);
		System.out.println(securityUser.getReferenceId());
		return securityUserRepository.save(securityUser);
	}

	public boolean disableUser(long securityUserId) {
		return securityUserRepository.disableUser(securityUserId) > 0;
	}
	
	
}
