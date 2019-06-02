package com.quarks.quarksassignment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Transactional
@Service
@Repository
public class EmployeeServiceImpl {
	
	@Autowired
	private EmployeeRepository employeeService;
	
	public void addEmployee(Employee employee) {
		employeeService.save(employee);
	}
	
	public void deleteRow(long id) {
		employeeService.deleteById(id);
	}
	
	public List<Employee> getEmployees(){
		return employeeService.findAll();
	}
	
	public void deleteAll() {
		employeeService.deleteAll();
	}
}
