package com.benchikh.demo.repository;

import com.benchikh.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
     Customer findByEmail(String email);
}
