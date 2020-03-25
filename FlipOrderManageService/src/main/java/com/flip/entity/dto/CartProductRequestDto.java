package com.flip.entity.dto;

import lombok.Data;

@Data
public class CartProductRequestDto {

	private Long cartId;
	
	private String userId;
	
	private String productId;
	
}
