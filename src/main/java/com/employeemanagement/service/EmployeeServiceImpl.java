package com.employeemanagement.service;


import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.mapper.EmployeeMapper;
import com.employeemanagement.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeMapper employeeMapper; // Inject the mapper

    /**
     * Get all employees (paginated).
     *
     * @param pageable Pagination and sorting configuration.
     * @return A paginated list of employees.
     */
    @Cacheable("employees")
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper.INSTANCE::toDTO);
    }

    /**
     * Get an employee by ID.
     *
     * @param id The ID of the employee.
     * @return The employee DTO.
     * @throws ResourceNotFoundException If no employee is found with the given ID.
     */
    public EmployeeDTO getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return EmployeeMapper.INSTANCE.toDTO(employee);
    }

    /**
     * Create a new employee.
     *
     * @param employeeDTO The employee data to create.
     * @return The created employee DTO.
     */
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO); // Use mapper
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(savedEmployee);
    }
    
    /**
     * Update an existing employee.
     *
     * @param id          The ID of the employee to update.
     * @param employeeDTO The updated employee data.
     * @return The updated employee DTO.
     * @throws ResourceNotFoundException If no employee is found with the given ID.
     */
    public EmployeeDTO updateEmployee(int id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        // Update fields
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        existingEmployee.setAge(employeeDTO.getAge());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.INSTANCE.toDTO(updatedEmployee);
    }

    /**
     * Delete an employee by ID.
     *
     * @param id The ID of the employee to delete.
     * @throws ResourceNotFoundException If no employee is found with the given ID.
     */
    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
        
    }

    /**
     * Search employees by name or department.
     *
     * @param query   The search term (name or department).
     * @param pageable Pagination and sorting configuration.
     * @return A paginated list of matching employees.
     */
    public Page<EmployeeDTO> searchEmployees(String query, Pageable pageable) {
        return employeeRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(query, query, pageable)
                .map(EmployeeMapper.INSTANCE::toDTO);
    }


}