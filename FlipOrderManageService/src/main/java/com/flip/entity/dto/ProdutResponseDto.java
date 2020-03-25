package com.flip.entity.dto;

import lombok.Data;

@Data
public class ProdutResponseDto {

	private String id;
	
	//private String productId;
	
	private String name;
	
	private String desc;
	
	private String status;
	
	private PriceDto price;
}
