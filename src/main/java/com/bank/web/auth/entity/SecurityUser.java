package com.bank.web.auth.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bank.web.base.AuditableEntity;
import com.bank.web.employee.entity.Employee;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Entity
public class SecurityUser extends AuditableEntity<Long>{
	
	public enum GlobalRole {
		SYSTEM_ADMIN, ADMIN, EMPLOYEE
	}
	
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private GlobalRole userRole;
	private long referenceId;
	private boolean isEnabled;
	private boolean isDisabled;
	
	public static SecurityUser from(Employee employee, GlobalRole userRole) {
		SecurityUser securityUser = new SecurityUser();
		securityUser.isEnabled = true;
		securityUser.isDisabled = false;
		securityUser.username = employee.getEmail();
		//TODO
		securityUser.password = "";
		securityUser.userRole = userRole;
		securityUser.referenceId = employee.getId();
		return securityUser;
	}
}
