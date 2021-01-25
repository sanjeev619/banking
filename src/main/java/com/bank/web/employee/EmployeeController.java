package com.bank.web.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.web.employee.entity.Employee;
import com.bank.web.employee.model.EmployeeForm;
import com.bank.web.employee.service.EmployeeService;

@RequestMapping("api")
@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("employee")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("employee/{employeeId}")
	public Employee getEmployee(@PathVariable long employeeId) {
		return employeeService.getEmployee(employeeId);
	}
	
	@PostMapping("employee")
	public Employee addEmployee(@RequestBody EmployeeForm employeeForm) {
		return employeeService.addEmployee(employeeForm);
	}
	
	@PostMapping("employee/{employeeId}")
	public Employee updateEmployee(@PathVariable long employeeId, @RequestBody EmployeeForm employeeForm) {
		return employeeService.updateEmployee(employeeId, employeeForm);
	}
	
	@PostMapping("employee/{employeeId}/delete")
	public Employee deleteEmployee(@PathVariable long employeeId) {
		return employeeService.deleteEmployee(employeeId);
	}
}
