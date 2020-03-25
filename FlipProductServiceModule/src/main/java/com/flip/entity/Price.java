package com.flip.entity;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Price {
	
	private Double actualPrice;
	
	private Double priorPrice;
	
	private Double discountOnPrice;
	

}
