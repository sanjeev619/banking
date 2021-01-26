package com.bank.web.security;

import org.springframework.security.core.GrantedAuthority;

import com.bank.web.auth.entity.SecurityUser.GlobalRole;

public class BankGrantedAuthority implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String role;
	
	public static final BankGrantedAuthority SUPER_ADMIN = new BankGrantedAuthority(GlobalRole.SYSTEM_ADMIN);
	public static final BankGrantedAuthority ADMIN =  new BankGrantedAuthority(GlobalRole.ADMIN);
	public static final BankGrantedAuthority EMPLOYEE =  new BankGrantedAuthority(GlobalRole.EMPLOYEE);
	
	
	public BankGrantedAuthority(GlobalRole globalRole) {
		this.role = globalRole.toString();
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

}
