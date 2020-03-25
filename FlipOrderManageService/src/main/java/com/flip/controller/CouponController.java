package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flip.entity.dto.CouponInfoRequestDto;
import com.flip.entity.dto.CouponProductInfoRequestDto;
import com.flip.entity.dto.CouponStatusRequestDto;
import com.flip.entity.dto.Response;
import com.flip.service.CouponService;

@RestController
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@PostMapping("/coupon")
	public Response addCoupon(@RequestBody CouponInfoRequestDto couponInfoRequestDto) {	
		return Response.builder().data(couponService.addCoupon(couponInfoRequestDto)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/coupon/{couponId}")
	public Response removeCoupon(@PathVariable("couponId") Long couponId) {	
		return Response.builder().data(couponService.removeCoupon(couponId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("/coupon/{couponId}")
	public Response findCouponByCouponId(@PathVariable("couponId") Long couponId) {	
		return Response.builder().data(couponService.findCouponByCouponId(couponId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("/allcoupons")
	public Response findallCoupons() {	
		return Response.builder().data(couponService.findallCoupons()).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}

	@PostMapping("/coupon/addproduct")
	public Response addCouponToProduct(@RequestBody CouponProductInfoRequestDto couponProductInfoRequestDto) {	
		return Response.builder().data(couponService.addCouponToProduct(couponProductInfoRequestDto)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/coupon/removeproduct")
	public Response removeCouponFromProduct(@RequestBody CouponProductInfoRequestDto couponProductInfoRequestDto) {	
		return Response.builder().data(couponService.removeCouponFromProduct(couponProductInfoRequestDto)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PostMapping("/coupon/elegibleproduct")
	public Response productEligibleForCoupon(@RequestBody CouponProductInfoRequestDto couponProductInfoRequestDto) {	
		return Response.builder().data(couponService.productEligibleForCoupon(couponProductInfoRequestDto)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PostMapping("/coupon/updatestatus")
	public Response updateCouponStatus(@RequestBody CouponStatusRequestDto couponStatusRequest) {	
		return Response.builder().data(couponService.updateCouponStatus(couponStatusRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
}
