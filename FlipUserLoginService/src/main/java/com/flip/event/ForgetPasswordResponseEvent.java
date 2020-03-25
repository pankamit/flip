package com.flip.event;

import lombok.Data;

@Data
public class ForgetPasswordResponseEvent {
	
	private String email;
	private String otp;
	private Integer validityTime;

}



	