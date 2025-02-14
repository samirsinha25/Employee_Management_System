package com.employeemanagement.service;

import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.ResourceNotFoundException;
import com.employeemanagement.mapper.EmployeeMapper;
import com.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetEmployeeById() {
        // Arrange
        int id = 1;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("John Doe");

            when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

            // Act
            EmployeeDTO result = employeeService.getEmployeeById(id);

            // Assert
            assertNotNull(result);
            assertEquals("John Doe", result.getName());
            verify(employeeRepository, times(1)).findById(id);
        
    }

    @Test
    void testGetEmployeeByIdThrowsException() {
        // Arrange
        int id = 1;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(id);
        });
        assertEquals("Employee not found with ID: 1", exception.getMessage());
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateEmployeeThrowsException() {
        // Arrange
        int id = 1;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Updated Name");

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(id, employeeDTO);
        });
        assertEquals("Employee not found with ID: 1", exception.getMessage());
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteEmployee() {
        // Arrange
        int id = 1;
        Employee employee = new Employee();
        employee.setId(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        // Act
        employeeService.deleteEmployee(id);

        // Assert
        verify(employeeRepository, times(1)).findById(id);
        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }

    @Test
    void testDeleteEmployeeThrowsException() {
        // Arrange
        int id = 1;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(id);
        });
        assertEquals("Employee not found with ID: 1", exception.getMessage());
        verify(employeeRepository, times(1)).findById(id);
    }

}