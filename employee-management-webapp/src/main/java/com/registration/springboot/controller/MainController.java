package com.registration.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {
	@GetMapping("login")
	public String login() {
		System.out.println("Calling login page");
		return "login";
	}
}
