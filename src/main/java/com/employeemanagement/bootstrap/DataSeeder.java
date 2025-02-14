package com.employeemanagement.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.employeemanagement.model.Role;
import com.employeemanagement.model.User;
import com.employeemanagement.repository.UserRepository;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) { // Only insert if no users exist

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN));

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles(Set.of(Role.ROLE_USER));

            userRepository.saveAll(Set.of(admin, user));

            System.out.println("Admin and User accounts created successfully!");
        }
    }
}
