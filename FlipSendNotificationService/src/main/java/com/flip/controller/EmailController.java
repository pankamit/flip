package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flip.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@GetMapping("/sendEmail")
	public String sendMessage() {
		//emailService.sendEmail();
		return "hello";
	}
}
