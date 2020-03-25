package com.flip.entity;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class OSLeptopInfo {
	
	private String osArchitecture;
	
	private String operatingSystem;
	
	private String systemArchitecture;
	
}
