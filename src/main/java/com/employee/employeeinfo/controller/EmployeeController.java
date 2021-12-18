package com.employee.employeeinfo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.employeeinfo.dto.EmployeeDto;
import com.employee.employeeinfo.entity.Employee;
import com.employee.employeeinfo.repository.EmployeeRepository;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee employee = new Employee();
		employee.setContactNumber(employeeDto.getContact());
		employee.setEmail(employeeDto.getEmail());
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employeeRepository.save(employee);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployee(@ApiParam(value = "ID of employee", required = true, example = "1") 
				@PathVariable("id") Integer employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			return new ResponseEntity<>(employee.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();

		if (employees == null || employees.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@ApiParam(value = "ID of employee", required = true, example = "1") 
													@PathVariable("id") Integer employeeId,@RequestBody EmployeeDto employeeDto) {
		Optional<Employee> employeeData = employeeRepository.findById(employeeId);

		if (employeeData.isPresent()) {
			Employee employee = employeeData.get();
			employee.setContactNumber(employeeDto.getContact());
			employee.setEmail(employeeDto.getEmail());
			employee.setFirstName(employeeDto.getFirstName());
			employee.setLastName(employeeDto.getLastName());
			employeeRepository.save(employee);
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@ApiParam(value = "ID of employee", required = true, example = "1") 
														@PathVariable("id") Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			employeeRepository.delete(employee.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
