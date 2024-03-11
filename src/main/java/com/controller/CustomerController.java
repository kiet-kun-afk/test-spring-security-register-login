package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Customer;
import com.repository.CustomerRepository;

@Controller
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@SuppressWarnings("null")
	@PostMapping("/handle-register")
	public String handleRegister(
		@RequestParam("email") String email, 
		@RequestParam("password") String password) {
		customerRepository.save(
				Customer
						.builder()
						.email(email)
						.password(passwordEncoder.encode(password))
						.build());
		return "redirect:/login";
	}

}
