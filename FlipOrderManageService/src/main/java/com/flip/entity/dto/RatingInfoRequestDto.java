package com.flip.entity.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RatingInfoRequestDto{
	
	private Long ratingId;
		
	private Long ratingPoint;
	
	private String reviewComment;
	
	private String reviewTitle;
	
	@NotNull
	private String productId;
	
	@NotNull
	private String userId;
	
	private Long[] deletedFileIds;
	
}
