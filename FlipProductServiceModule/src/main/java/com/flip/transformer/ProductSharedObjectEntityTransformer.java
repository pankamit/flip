package com.flip.transformer;



import org.springframework.beans.BeanUtils;

import com.flip.entity.Product;
import com.flip.sharedObject.ProductSharedObject;

public class ProductSharedObjectEntityTransformer {

	public static Product apply(ProductSharedObject productSharedObject) {
		Product product = new Product();
		
		BeanUtils.copyProperties(productSharedObject,product);
		return product;
	}
	
}
