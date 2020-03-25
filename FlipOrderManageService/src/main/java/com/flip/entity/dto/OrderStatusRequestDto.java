package com.flip.entity.dto;

import com.flip.enumeration.OrderStatus;

import lombok.Data;

@Data
public class OrderStatusRequestDto {

	private String userId;
	
	private Long orderId;
	
	private OrderStatus status;

	private String activityComment;

}

