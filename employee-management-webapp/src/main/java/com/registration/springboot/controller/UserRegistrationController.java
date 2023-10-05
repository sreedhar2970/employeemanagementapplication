package com.registration.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registration.springboot.dto.UserRegistrationDto;
import com.registration.springboot.service.UserService;

@Controller
@RequestMapping
public class UserRegistrationController {
	
	private UserService userService;
	
	public UserRegistrationController(UserService userService){
		super();
		this.userService=userService;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		System.out.println("userRegistrationDto");
		return new UserRegistrationDto();
	}
	
	@GetMapping("registration")
	public String showRegistrationForm() {
		System.out.println("registration page call");
		return "registration";
	}
	
	@PostMapping("registration")
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
		System.out.println("userRegistrationDto call");
		userService.save(userRegistrationDto);
		System.out.println("success call");
		return "redirect:/registration?success";		
	}
}

