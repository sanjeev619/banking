package com.bank.web.auth.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.web.base.AuditableEntity;
import com.bank.web.employee.entity.Employee;

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
		
		securityUser.isEnabled = true;
		securityUser.isAccountNonExpired = true;
		securityUser.isAccountNonLocked = true;
		securityUser.isCredentialsNonExpired = true;
		return securityUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
}
