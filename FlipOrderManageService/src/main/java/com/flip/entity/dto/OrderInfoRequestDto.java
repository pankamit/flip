package com.flip.entity.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.flip.enumeration.OrderStatus;

import lombok.Data;

@Data
public class OrderInfoRequestDto {
	
	private String userId;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus effOrderStatus;
	
	private Double amount;
	
	private Integer quantity;
	
	private String[] productIdLst;
	
}


