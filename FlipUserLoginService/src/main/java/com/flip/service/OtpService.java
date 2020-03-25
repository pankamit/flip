package com.flip.service;

import java.util.List;

import com.flip.dto.OtpRequest;
import com.flip.entity.OtpEntity;

public interface OtpService {

	public OtpEntity saveOtp(OtpEntity otpRequest);
	
	public List<OtpEntity> getOtp(String userName);
	
	public Boolean validateOtp(OtpRequest otpRequest);
	
}
