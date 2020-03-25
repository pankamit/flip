package com.flip.sharedObject;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.flip.enumeration.Colour;
import com.flip.enumeration.ProductStatus;

import lombok.Data;


@Data
public class ProductPicSharedObject{
	
	private String size;
		
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	
	@Enumerated(EnumType.STRING)
	private Colour picColour;
	
	
}
