package com.bank.web.customer.model;

import com.bank.web.customer.entity.Customer.GENDER;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerForm {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private GENDER gender;
	private String address;
	private String panNo;
	private String aadhaarNo;
}
