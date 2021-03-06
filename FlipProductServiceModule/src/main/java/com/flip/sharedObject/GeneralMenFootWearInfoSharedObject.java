package com.flip.sharedObject;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.flip.enumeration.Gender;
import com.flip.enumeration.Occasion;

import lombok.Data;

@Data
public class GeneralMenFootWearInfoSharedObject extends ProductSharedObject{
	
	private String outerMaterial;
	
	private String modelName;
	
	private Gender idealUsedFor;
	
	@Enumerated(EnumType.STRING)
	private Occasion occasion;
	
	private String soleMaterial;
	
	private String closure;
	
	private String Weight;
	
	private String salesPackage;
	
	private String tipShape;
	
	private String careInstructions;
	
	private String genericName;
	
	
}
