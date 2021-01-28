package com.bank.web.auth.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.web.base.AuditableEntity;
import com.bank.web.employee.entity.Employee;
import com.bank.web.security.BankGrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Entity
public class SecurityUser extends AuditableEntity<Long> implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum GlobalRole {
		SYSTEM_ADMIN, ADMIN, EMPLOYEE
	}
	
	@Column(unique = true)
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private GlobalRole userRole;
	private long referenceId;
	private boolean isEnabled;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	
	public static SecurityUser from(Employee employee, GlobalRole userRole) {
		SecurityUser securityUser = new SecurityUser();
		securityUser.username = employee.getEmail();
		//TODO
		securityUser.password = "";
		securityUser.userRole = userRole;
		securityUser.referenceId = employee.getId();
		
		securityUser.isAccountNonExpired = true;
		securityUser.isAccountNonLocked = true;
		securityUser.isCredentialsNonExpired = true;
		return securityUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		switch (this.userRole) {
			case SYSTEM_ADMIN:
				return Arrays.asList(BankGrantedAuthority.SUPER_ADMIN, BankGrantedAuthority.ADMIN, BankGrantedAuthority.EMPLOYEE);
			case ADMIN:
				return Arrays.asList(BankGrantedAuthority.ADMIN, BankGrantedAuthority.EMPLOYEE);
			default:
				return Arrays.asList(BankGrantedAuthority.EMPLOYEE);
		}
	}

	public static SecurityUser defaultMasterUser() {
		SecurityUser securityUser = new SecurityUser();
		securityUser.username = "admin";
		securityUser.password = "Qwerty@123";
		securityUser.userRole = GlobalRole.SYSTEM_ADMIN;
		securityUser.isEnabled = true;
		securityUser.isAccountNonExpired = true;
		securityUser.isAccountNonLocked = true;
		securityUser.isCredentialsNonExpired = true;
		return securityUser;
	}
}
