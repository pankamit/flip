package com.flip.sharedObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CategorySharedObject{
	
	private String name;
	
	private String code;
	
	private String desc;	
	
	private List<SubCategorySharedObject> subCategorySharedObjectLst = new ArrayList<>();
	
}
