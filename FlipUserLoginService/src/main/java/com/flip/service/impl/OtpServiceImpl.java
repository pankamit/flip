package com.flip.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flip.dto.OtpRequest;
import com.flip.entity.OtpEntity;
import com.flip.repository.OtpRepository;
import com.flip.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;

	@Override
	public OtpEntity saveOtp(OtpEntity otpRequest) {
		return otpRepository.save(otpRequest);
	}

	@Override
	public List<OtpEntity> getOtp(String userName) {
		return otpRepository.findByEmail(userName)
				.orElseThrow(() -> new RuntimeException("OTP is not valid for given username"));
	}

	@Override
	public Boolean validateOtp(OtpRequest otpRequest) {

		List<OtpEntity> otpEntityLst = otpRepository.findByEmail(otpRequest.getEmail())
				.orElseThrow(() -> new RuntimeException("OTP is not valid for given username"));

		return otpEntityLst.stream()
				.filter(otpEntity -> otpEntity.getOtp().equals(otpRequest.getOtp())
						&& isOtpNonExpired(otpEntity.getValidityOtpTime()))
				.findFirst().isPresent();
	}

	private Boolean isOtpNonExpired(Date validityTime) {
		Calendar calendar = Calendar.getInstance();		
		return (validityTime.compareTo(calendar.getTime()) >= 0);
	}

}
