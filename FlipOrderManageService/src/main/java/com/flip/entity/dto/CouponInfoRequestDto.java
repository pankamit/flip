package com.flip.entity.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.flip.enumeration.CouponStatus;

import lombok.Data;

@Data
public class CouponInfoRequestDto {
	
	private String couponCode;
	
	private Double couponAmount;
	
	private Double couponDiscountPercent;
	
	private Boolean isPercentageOff;
	
	private Date validityStartDate;
	
	private Date validityEndDate;
	
	@Enumerated(EnumType.STRING)
	private CouponStatus couponStatus;
	
	private String couponDesc;
	
	private String[] productIdLst;
}

