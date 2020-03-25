package com.flip.entity.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class CartInfoResponseDto {

	private Long id;
	
	private String userId;

	private Double amount;
	
	private Integer productQuntity;

	private List<ProdutResponseDto> productResponseDtoLst = new ArrayList<>(); 
	
}
