package com.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entity.Customer;
import com.entity.Staff;
import com.repository.CustomerRepository;
import com.repository.StaffRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	StaffRepository staffRepository;

	public MyUserDetailsService(CustomerRepository customerRepository, StaffRepository staffRepository) {
		this.customerRepository = customerRepository;
		this.staffRepository = staffRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Customer customer = customerRepository.findByEmail(username);
		if (customer == null) {
			Staff staff = staffRepository.findByUsername(username);
			if (staff == null) {
				throw new UsernameNotFoundException("Can not found with username : " + username);
			}
			return StaffRoot.create(staff);
		}
		return CustomerRoot.create(customer);
	}
}
