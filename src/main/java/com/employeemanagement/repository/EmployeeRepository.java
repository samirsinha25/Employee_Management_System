package com.employeemanagement.repository;


import com.employeemanagement.entity.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Page<Employee> findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String department, Pageable pageable);
}