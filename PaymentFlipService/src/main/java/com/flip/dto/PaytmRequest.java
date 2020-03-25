package com.flip.dto;

import lombok.Data;

@Data
public class PaytmRequest {

	private String customerId;
	
	private String transactionAmount;
	
	private String orderId;
	
}


