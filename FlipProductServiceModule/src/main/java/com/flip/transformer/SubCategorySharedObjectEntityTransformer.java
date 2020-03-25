package com.flip.transformer;



import org.springframework.beans.BeanUtils;

import com.flip.entity.SubCategory;
import com.flip.sharedObject.SubCategorySharedObject;

public class SubCategorySharedObjectEntityTransformer {

	public static SubCategory apply(SubCategorySharedObject subCategorySharedObject) {
		SubCategory subCategory = new SubCategory();
		
		BeanUtils.copyProperties(subCategorySharedObject,subCategory);
		return subCategory;
	}
	
}
