package com.employeemanagement.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String department;

    @Min(18)
    private int age;

    @Email
    private String email;

    @DecimalMin(value = "30000.00")
    private BigDecimal salary;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    public int getId() {
  		return id;
  	}

  	public void setId(int id) {
  		this.id = id;
  	}

  	public String getName() {
  		return name;
  	}

  	public void setName(String name) {
  		this.name = name;
  	}

  	public String getDepartment() {
  		return department;
  	}

  	public void setDepartment(String department) {
  		this.department = department;
  	}

  	public int getAge() {
  		return age;
  	}

  	public void setAge(int age) {
  		this.age = age;
  	}

  	public String getEmail() {
  		return email;
  	}

  	public void setEmail(String email) {
  		this.email = email;
  	}

  	public BigDecimal getSalary() {
  		return salary;
  	}

  	public void setSalary(BigDecimal salary) {
  		this.salary = salary;
  	}

  	public LocalDateTime getCreatedAt() {
  		return createdAt;
  	}

  	public void setCreatedAt(LocalDateTime createdAt) {
  		this.createdAt = createdAt;
  	}

  	public LocalDateTime getUpdatedAt() {
  		return updatedAt;
  	}

  	public void setUpdatedAt(LocalDateTime updatedAt) {
  		this.updatedAt = updatedAt;
  	}
}