package com.employeemanagement.controller;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.employeemanagement.dto.LoginRequest;
import com.employeemanagement.secret.util.JwtUtil;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	    private final AuthenticationManager authenticationManager;
	    private final JwtUtil jwtUtil;
	    private final UserDetailsService userDetailsService;

	    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
	        this.authenticationManager = authenticationManager;
	        this.jwtUtil = jwtUtil;
	        this.userDetailsService = userDetailsService;
	    }

	    @PostMapping("/login")
	    public String login(@RequestBody LoginRequest loginRequest) {
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
	        return jwtUtil.generateToken(userDetails);
	    }
}
