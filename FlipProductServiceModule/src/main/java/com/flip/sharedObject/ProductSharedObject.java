package com.flip.sharedObject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.flip.entity.Price;
import com.flip.enumeration.ProductStatus;

import lombok.Data;


@Data
public class ProductSharedObject {
	
	private String name;
	
	private String desc;
	
	private Long categoryId;
	
	private Long subCatagoryId;
	
	private Long brandId;
	
	private String seller;
	
	private Price price;
		
	private Long quantity;
	
	@Enumerated(EnumType.STRING)
	private ProductStatus status = ProductStatus.NOT_VERIFIED;
	
	private List<ProductPicSharedObject> picLst = new ArrayList<>();
	
}


