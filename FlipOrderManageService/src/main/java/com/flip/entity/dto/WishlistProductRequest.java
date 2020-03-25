package com.flip.entity.dto;

import lombok.Data;

@Data
public class WishlistProductRequest {

	private Long wishlistId;
	
	private String userId;
	
	private String productId;
	
}
