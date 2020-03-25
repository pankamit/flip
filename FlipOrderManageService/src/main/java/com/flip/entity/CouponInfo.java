package com.flip.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.flip.enumeration.CouponStatus;

import lombok.Data;

@Entity
@Data
public class CouponInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String couponCode;
	
	private Double couponAmount;
	
	private Double couponDiscountPercent;
	
	private Boolean isPercentageOff;
	
	private LocalDateTime validityStartDate;
	
	private LocalDateTime validityEndDate;
	
	private String couponDesc;
	
	@Enumerated(EnumType.STRING)
	private CouponStatus couponStatus = CouponStatus.ACTIVE;
	
	@OneToMany
	@JoinColumn(name = "COUPON_ID")
	private List<CouponProductInfo> couponProductInfoLst = new ArrayList<>();
}

