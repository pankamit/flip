package com.flip.entity.dto;

import lombok.Data;

@Data
public class WishlistRequest {

	private Long wishlistId;
	
	private String wishlistName;
	
	private String userId;
	
}
