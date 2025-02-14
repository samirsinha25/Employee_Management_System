package com.employeemanagement.controller;


import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;

import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable("id") Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
       
    }
    
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
        @RequestParam(name = "page" , defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "sort", defaultValue = "name,asc") String sort 
    ) {
        // Split the sort parameter into field and direction
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

        // Create a Pageable with sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }
  
    @GetMapping("/search")
    public Page<EmployeeDTO> searchEmployees(@RequestParam String query, 
    		@RequestParam(name = "page" , defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "name,asc") String sort ) {
    	 String[] sortParams = sort.split(",");
         String sortField = sortParams[0];
         Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

         // Create a Pageable with sorting
         Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return employeeService.searchEmployees(query, pageable);
    }
}