package com.registration.springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.registration.springboot.dto.UserRegistrationDto;
import com.registration.springboot.model.User;

public interface UserService extends UserDetailsService {
	User save(UserRegistrationDto registrationDto);
	
}
