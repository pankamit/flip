package com.flip.service;

import java.util.List;

import com.flip.entity.CouponInfo;
import com.flip.entity.dto.CouponInfoRequestDto;
import com.flip.entity.dto.CouponProductInfoRequestDto;
import com.flip.entity.dto.CouponStatusRequestDto;

public interface CouponService {

	public CouponInfo addCoupon(CouponInfoRequestDto couponInfoRequestDto);
	
	public Long removeCoupon(Long couponId);
	
	public CouponInfo findCouponByCouponId(Long couponId);
	
	public List<CouponInfo> findallCoupons();
	
	public CouponInfo addCouponToProduct(CouponProductInfoRequestDto couponProductInfoRequestDto);
	
	public CouponInfo removeCouponFromProduct(CouponProductInfoRequestDto couponProductInfoRequestDto);
	
	public Boolean productEligibleForCoupon(CouponProductInfoRequestDto couponProductInfoRequestDto);
	
	public CouponInfo updateCouponStatus(CouponStatusRequestDto couponStatusRequest);
	
	public void updateEffectiveCouponStatus();
	
}
