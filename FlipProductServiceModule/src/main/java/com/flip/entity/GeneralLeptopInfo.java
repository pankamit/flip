package com.flip.entity;

import javax.persistence.Embeddable;

import com.flip.enumeration.Colour;

import lombok.Data;

@Embeddable
@Data
public class GeneralLeptopInfo {
	
	private String salesPackage;
	
	private String modelNumbere;
	
	private String partNumber;
	
	private String series;
	
	private Colour colour;
	
	private String type;
	
	private String suitable;
	
	private String batteryBackUp;
	
	private String powerSuppy;
	
	private Boolean isMsOfficeProvided;
	
}
