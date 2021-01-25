package com.bank.web.employee.model;

import com.bank.web.auth.entity.SecurityUser.GlobalRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeForm {
	private String firstName;
	private String lastName;
	private String email;
	private String code;
	private String band;
	private String title;
	private String phone;
	
	private GlobalRole userRole = GlobalRole.EMPLOYEE;
}
