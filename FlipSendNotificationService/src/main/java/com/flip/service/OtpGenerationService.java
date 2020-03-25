package com.flip.service;

import java.util.Random;

public class OtpGenerationService {

	
	public String generateOTP() {
		String numbers = "0123456789"; 
		Random rndm_method = new Random(); 
		int len = numbers.length();
		
       // char[] password = new char[len]; 
        String pass = "";
		
        for (int i = 0; i < 6; i++) 
        { 
            // Use of charAt() method : to get character value 
            // Use of nextInt() as it is scanning the value as int 
            pass  += numbers.charAt(rndm_method.nextInt(len)); 
  
        } 
        return pass; 
	}
	
}
