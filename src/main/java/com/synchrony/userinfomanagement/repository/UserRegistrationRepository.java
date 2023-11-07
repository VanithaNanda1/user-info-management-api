package com.synchrony.userinfomanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synchrony.userinfomanagement.entity.UserRegistration;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
	
	UserRegistration findById(long id);
	

}
