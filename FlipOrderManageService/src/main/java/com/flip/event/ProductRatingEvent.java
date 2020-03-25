package com.flip.event;

import lombok.Data;

@Data
public class ProductRatingEvent {
	
	private String userId;

	private String productId;
	
	private Long ratingId;
	
	private Long ratingPoint;
	
}
