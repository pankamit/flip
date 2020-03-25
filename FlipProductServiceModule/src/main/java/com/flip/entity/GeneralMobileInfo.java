package com.flip.entity;

import javax.persistence.Embeddable;

import com.flip.enumeration.Colour;

import lombok.Data;

@Embeddable
@Data
public class GeneralMobileInfo {
	
	private String inTheBox;
	
	private String modelNumbere;
	
	private String modelName;
	
	private String browseType;
	
	private String simType;
	
	private Boolean isHybridSimSlot;
	
	private Boolean isTouchscreen;
	
	private Boolean isOtgCompatible;
	
}
