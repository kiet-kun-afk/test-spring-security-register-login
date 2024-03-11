package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findByEmail(String email);

	// Customer findByPhoneNumber(Integer phoneNumber);
}
