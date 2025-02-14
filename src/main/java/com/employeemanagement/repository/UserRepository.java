package com.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}