package com.flip.entity.dto;

import java.util.Date;
import com.flip.enumeration.OrderStatus;
import lombok.Data;

@Data
public class OrderActivityDto {

	private Long orderId;
	
	private OrderStatus status;
			
	private Date orderDate;

	private String activityComment;

}

