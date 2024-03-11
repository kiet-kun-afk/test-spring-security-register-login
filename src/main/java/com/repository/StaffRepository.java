package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

    Staff findByUsername(String username);
}
