package com.employee.employeeinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.employeeinfo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
