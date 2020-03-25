package com.flip.entity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.flip.enumeration.Occasion;

import lombok.Data;

@Embeddable
@Data
public class GeneralMenTopWeerInfo {
	
	private String type;
	
	private String sleeve;
	
	private String fit;
	
	private String fabric;
	
	@Enumerated(EnumType.STRING)
	private Occasion occasion;
	
	private String neckType;
	
	private String pattern;
	
	private String fabricCare;
	
	private String salesPackage;
	
	private String genericName;
}
