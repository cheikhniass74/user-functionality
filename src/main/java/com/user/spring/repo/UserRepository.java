package com.user.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

	User findByforgotPasswordVerificationCode(String forgotPasswordCode);
	

}
