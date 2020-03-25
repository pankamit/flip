package com.flip.service;

import com.flip.entity.CartInfo;
import com.flip.entity.dto.CartInfoResponseDto;
import com.flip.entity.dto.CartProductRequestDto;
import com.flip.entity.dto.CouponCartInfoRequestDto;

public interface CartService {

	public CartInfoResponseDto findByCartId(Long cartId);
	
	public CartInfo addProduct(CartProductRequestDto cartProductRequest);
	
	public CartInfo removeProduct(CartProductRequestDto cartProductRequest);
	
	public CartInfo addCart(String userId);
	
	public Long removeCart(String userId);
	
	public CartInfo addCoupon(CouponCartInfoRequestDto couponCartInfoRequest);
	
	public CartInfo removeCoupon(String userId);
}
