package com.bank.web.employee.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bank.web.auth.entity.SecurityUser;
import com.bank.web.auth.service.AuthService;
import com.bank.web.employee.entity.Employee;
import com.bank.web.employee.model.EmployeeForm;
import com.bank.web.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private AuthService authService;
	
	public List<Employee> getAllEmployees() {
		return employeeRepository.findByIsActiveTrue();
	}

	public Employee getEmployee(long employeeId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
		if(! optionalEmployee.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Employee Not Found For the given Id "+employeeId);
		}
		return optionalEmployee.get();
	}

	@Transactional
	public Employee addEmployee(EmployeeForm employeeForm) {
		Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeForm.getEmail());
		if(optionalEmployee.isPresent() && optionalEmployee.get().isActive()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Employee already exists with email id "+employeeForm.getEmail());
		}
		
		Employee employee = optionalEmployee.isPresent() ? optionalEmployee.get() : Employee.from(employeeForm);
		employee.setActive(true);
		employee = employeeRepository.save(employee);
		
		SecurityUser securityUser = authService.addUser(employee, employeeForm.getUserRole());
		employee.setSecurityUserId(securityUser.getId());
		return employeeRepository.save(employee);
	}

	@Transactional
	public Employee updateEmployee(long employeeId, EmployeeForm employeeForm) {
		Employee employee = getEmployee(employeeId);
		employee.update(employeeForm);
		return employeeRepository.save(employee);
	}

	@Transactional
	public Employee deleteEmployee(long employeeId) {
		System.out.println("employeeId "+employeeId);
		Employee employee = getEmployee(employeeId);
		if(! employee.isActive()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Employee is deleted with Id "+employeeId);
		}
		employee.setActive(false);
		authService.disableUser(employee.getSecurityUserId());
		return employeeRepository.save(employee);
	}

	
}
