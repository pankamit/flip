package com.flip.transformer;



import org.springframework.beans.BeanUtils;

import com.flip.entity.Category;
import com.flip.sharedObject.CategorySharedObject;

public class CategorySharedObjectEntityTransformer {

	public static Category apply(CategorySharedObject categorySharedObject) {
		Category category = new Category();
		
		BeanUtils.copyProperties(categorySharedObject,category);
		return category;
	}
	
}
