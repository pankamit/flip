package com.flip.service;

import java.util.List;

import com.flip.entity.Category;
import com.flip.entity.SubCategory;
import com.flip.sharedObject.SubCategorySharedObject;


public interface SubCategoryService {

	public SubCategory addSubCategory(SubCategorySharedObject subCategory);
	
	public SubCategory getSubCategoryById(Long id);
	
	public List<SubCategory> getAllSubCategory();
	
	public SubCategory updateSubCategory(Long id,SubCategory subCategory);
	
	public SubCategory deleteSubCategoryById(Long id);
	
	public List<SubCategory> addSubCategoryList(List<SubCategorySharedObject> subCategorySharedObjectLst,Category category);
	
}
