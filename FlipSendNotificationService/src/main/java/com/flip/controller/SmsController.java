package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.flip.service.SmsService;

@RestController
public class SmsController {

	@Autowired
	private SmsService smsService;
	
	@GetMapping("/sendSms")
	public String sendMessage() {
		return smsService.sendSms();
	}
	
	@GetMapping("/checkAllStatus")
	public String checkAllSmsStatus() {
		smsService.sendSmsAsynchronous();
		return "hello";
	}
	
	@GetMapping("/checkSmsStatus/{sid}")
	public String checkSmsStatus(@PathVariable("sid") String sid) {
		return smsService.checkStatusOfSms(sid);
	}
	
}
