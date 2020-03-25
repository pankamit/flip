package com.flip.service;

import java.util.List;

import com.flip.entity.Category;
import com.flip.sharedObject.CategorySharedObject;


public interface CategoryService {

	public Category addCategory(CategorySharedObject category);
	
	public Category getCategoryById(Long id);
	
	public List<Category> getAllCategory();
	
	public Category updateCategory(Long id,Category category);
	
	public Category deleteCategoryById(Long id);
	
}
