package com.employeemanagement.repository;

import com.employeemanagement.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Configures an in-memory database and enables repository testing
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        // Populate the database with sample data before each test
        Employee employee1 = new Employee();
        employee1.setName("John Doe");
        employee1.setDepartment("Engineering");
        employee1.setAge(30);
        employee1.setEmail("john.doe@example.com");
        employee1.setSalary(BigDecimal.valueOf(50000));

        Employee employee2 = new Employee();
        employee2.setName("Jane Smith");
        employee2.setDepartment("Human Resources");
        employee2.setAge(25);
        employee2.setEmail("jane.smith@example.com");
        employee2.setSalary(BigDecimal.valueOf(45000));

        Employee employee3 = new Employee();
        employee3.setName("Alice Johnson");
        employee3.setDepartment("Engineering");
        employee3.setAge(28);
        employee3.setEmail("alice.johnson@example.com");
        employee3.setSalary(BigDecimal.valueOf(48000));

        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
    }

    @Test
    void testFindByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase() {
        // Arrange
        String query = "engineer";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Employee> result = employeeRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(query, query, pageable);

        // Assert
        assertThat(result.getTotalElements()).isEqualTo(2); // Two employees match "Engineering"
        assertThat(result.getContent().stream().map(Employee::getName))
                .containsExactlyInAnyOrder("John Doe", "Alice Johnson");
    }

    @Test
    void testFindByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase_NoMatch() {
        // Arrange
        String query = "nonexistent";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Employee> result = employeeRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(query, query, pageable);

        // Assert
        assertThat(result.getTotalElements()).isEqualTo(0); // No matches
    }

    @Test
    void testFindByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase_Pagination() {
        // Arrange
        String query = "engineering";
        Pageable pageable = PageRequest.of(0, 1); // First page, size 1

        // Act
        Page<Employee> result = employeeRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(query, query, pageable);

        // Assert
        assertThat(result.getTotalElements()).isEqualTo(2); // Total matching records
        assertThat(result.getContent().size()).isEqualTo(1); // Only one record on this page
    }
}