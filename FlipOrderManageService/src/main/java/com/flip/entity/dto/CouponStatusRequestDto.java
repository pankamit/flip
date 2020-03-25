package com.flip.entity.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.flip.enumeration.CouponStatus;

import lombok.Data;

@Data
public class CouponStatusRequestDto {

	private Long couponId;
	
	@Enumerated(EnumType.STRING)
	private CouponStatus couponStatus;
	
}
