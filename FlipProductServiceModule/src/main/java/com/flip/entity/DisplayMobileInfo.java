package com.flip.entity;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class DisplayMobileInfo {
	
	private String displaySize;
	
	private String resolution;
	
	private String resolutionType;
	
	private String gpu;
	
	private String displayColours;
	
	private String otherDisplayFeature;
	
}
