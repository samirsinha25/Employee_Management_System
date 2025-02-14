package com.employeemanagement.controller;

import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeeById() {
        // Arrange
        Integer id = 1;
        EmployeeDTO mockEmployee = new EmployeeDTO();
        mockEmployee.setId(1);
        mockEmployee.setName("John Doe");
        when(employeeService.getEmployeeById(id)).thenReturn(mockEmployee);

        // Act
        EmployeeDTO result = employeeController.getEmployeeById(id);

        // Assert
        assertEquals("John Doe", result.getName());
        verify(employeeService, times(1)).getEmployeeById(id);
    }

    @Test
    void testCreateEmployee() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setAge(30);
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setSalary(BigDecimal.valueOf(50000));

        EmployeeDTO createdEmployee = new EmployeeDTO();
        createdEmployee.setId(1);
        createdEmployee.setName("John Doe");
        createdEmployee.setDepartment("Engineering");
        createdEmployee.setAge(30);
        createdEmployee.setEmail("john.doe@example.com");
        createdEmployee.setSalary(BigDecimal.valueOf(50000));

        when(employeeService.createEmployee(employeeDTO)).thenReturn(createdEmployee);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.createEmployee(employeeDTO);

        // Assert
        assertEquals("John Doe", response.getBody().getName());
        verify(employeeService, times(1)).createEmployee(employeeDTO);
    }

    @Test
    void testUpdateEmployee() {
        // Arrange
        int id = 1;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Updated Name");

        EmployeeDTO updatedEmployee = new EmployeeDTO();
        updatedEmployee.setId(id);
        updatedEmployee.setName("Updated Name");

        when(employeeService.updateEmployee(id, employeeDTO)).thenReturn(updatedEmployee);

        // Act
        EmployeeDTO result = employeeController.updateEmployee(id, employeeDTO);

        // Assert
        assertEquals("Updated Name", result.getName());
        verify(employeeService, times(1)).updateEmployee(id, employeeDTO);
    }

    @Test
    void testDeleteEmployee() {
        // Arrange
        int id = 1;

        doNothing().when(employeeService).deleteEmployee(id);

        // Act
        employeeController.deleteEmployee(id);

        // Assert
        verify(employeeService, times(1)).deleteEmployee(id);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setName("John Doe");

        Page<EmployeeDTO> mockPage = new PageImpl<>(Collections.singletonList(employeeDTO), pageable, 1);
        when(employeeService.getAllEmployees(any(Pageable.class))).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<EmployeeDTO>> response = employeeController.getAllEmployees(0, 10, "name,asc");

        // Assert
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("John Doe", response.getBody().getContent().get(0).getName());
        verify(employeeService, times(1)).getAllEmployees(any(Pageable.class));
    }

    @Test
    void testSearchEmployees() {
        // Arrange
        String query = "Engineering";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1);
        employeeDTO.setName("John Doe");

        Page<EmployeeDTO> mockPage = new PageImpl<>(Collections.singletonList(employeeDTO), pageable, 1);
        when(employeeService.searchEmployees(eq(query), any(Pageable.class))).thenReturn(mockPage);

        // Act
        Page<EmployeeDTO> result = employeeController.searchEmployees(query, 0, 10, "name,asc");

        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals("John Doe", result.getContent().get(0).getName());
        verify(employeeService, times(1)).searchEmployees(eq(query), any(Pageable.class));
    }
}
