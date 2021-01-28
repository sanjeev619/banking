package com.bank.web.customer.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bank.web.base.AuditableEntity;
import com.bank.web.customer.model.CustomerForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends AuditableEntity<Long>{
	
	public enum GENDER {
		NO_REVEAL, MALE, FEMALE, OTHER
	}
	
	//TODO add identity docs and use s3 service
	
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	@Enumerated(EnumType.STRING)
	private GENDER gender;
	private String address;
	private String panNo;
	private String aadhaarNo;
	private boolean isActive;
	
	
	public static Customer from(CustomerForm customerForm) {
		Customer customer = new Customer();
		customer.update(customerForm);
		customer.setActive(true);
		return customer;
	}

	public void update(CustomerForm customerForm) {
		this.firstName = customerForm.getFirstName();
		this.lastName = customerForm.getLastName();
		this.email = customerForm.getEmail();
		this.phone = customerForm.getPhone();
		this.gender = customerForm.getGender();
		this.address = customerForm.getAddress();
		this.panNo = customerForm.getPanNo();
		this.aadhaarNo = customerForm.getAadhaarNo();
	}

	public String getFullName() {
		if(firstName == null)
			firstName = "";
		if(lastName == null)
			lastName = "";
		return (firstName+" "+lastName).trim();
	}
	
	
}
