package com.benchikh.demo.controllers;


import com.benchikh.demo.config.JwtUtil;
import com.benchikh.demo.dto.AuthenticationRequest;
import com.benchikh.demo.dto.AuthenticationResponse;
import com.benchikh.demo.model.Customer;
import com.benchikh.demo.repository.CustomerRepository;
import com.benchikh.demo.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthenticationControllers {
    private final JwtUtil jwtUtil;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private AuthenticationManager authenticationManager;

    public AuthenticationControllers(JwtUtil jwtUtil, CustomerRepository customerRepository, CustomerService customerService, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
         authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
         final UserDetails user = customerRepository.findByEmail(request.getEmail());

         if (user != null)
             return ResponseEntity.ok(jwtUtil.generateToken(user));
         return ResponseEntity.status(400).body("An error was occured");
    }

}
