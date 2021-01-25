package com.bank.web.employee.entity;

import javax.persistence.Entity;

import com.bank.web.base.AuditableEntity;
import com.bank.web.employee.model.EmployeeForm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Entity
public class Employee extends AuditableEntity<Long>{
	private String firstName;
	private String lastName;
	private String email;
	private String code;
	private String band;
	private long securityUserId;
	private String title;
	private String phone;
	private boolean isActive;
	
	public static Employee from(EmployeeForm employeeForm) {
		Employee employee = new Employee();
		employee.email = employeeForm.getEmail();
		employee.isActive = true;
		employee.update(employeeForm);
		return employee;
	}

	public void update(EmployeeForm employeeForm) {
		this.firstName = employeeForm.getFirstName();
		this.lastName = employeeForm.getLastName();
		this.code = employeeForm.getCode();
		this.band = employeeForm.getBand();
		this.title = employeeForm.getTitle();
		this.phone = employeeForm.getPhone();
	}
}
