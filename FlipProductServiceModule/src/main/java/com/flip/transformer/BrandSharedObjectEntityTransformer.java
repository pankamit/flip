package com.flip.transformer;



import org.springframework.beans.BeanUtils;

import com.flip.entity.Brand;
import com.flip.sharedObject.BrandSharedObject;

public class BrandSharedObjectEntityTransformer {

	public static Brand apply(BrandSharedObject brandSharedObject) {
		Brand brand = new Brand();
		
		BeanUtils.copyProperties(brandSharedObject,brand);
		return brand;
	}
	
}
