package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flip.entity.dto.CartProductRequestDto;
import com.flip.entity.dto.CouponCartInfoRequestDto;
import com.flip.entity.dto.Response;
import com.flip.service.CartService;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	
	@GetMapping("/cart/{cartId}")
	public Response findCartByCartId(@PathVariable("cartId") Long cartId) {	
		return Response.builder().data(cartService.findByCartId(cartId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PostMapping("/cart/addproduct")
	public Response addProductToCart(@RequestBody CartProductRequestDto cartProductRequest) {	
		return Response.builder().data(cartService.addProduct(cartProductRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/cart/removeproduct")
	public Response removeProductFromCart(@RequestBody CartProductRequestDto cartProductRequest) {	
		return Response.builder().data(cartService.removeProduct(cartProductRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PostMapping("/cart/addcoupon")
	public Response addCouponToCart(@RequestBody CouponCartInfoRequestDto couponCartInfoRequest) {	
		return Response.builder().data(cartService.addCoupon(couponCartInfoRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/cart/removecoupon/{userId}")
	public Response removeCouponFromCart(@PathVariable("userId") String userId) {	
		return Response.builder().data(cartService.removeCoupon(userId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	
	
}
