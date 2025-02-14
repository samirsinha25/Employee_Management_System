package com.employeemanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.employeemanagement.dto.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(int id);
    EmployeeDTO updateEmployee(int id, EmployeeDTO employeeDTO);
    void deleteEmployee(int id);
    Page<EmployeeDTO> getAllEmployees(Pageable pageable);
    Page<EmployeeDTO> searchEmployees(String query, Pageable pageable);
}
