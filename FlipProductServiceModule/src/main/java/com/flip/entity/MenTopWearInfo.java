package com.flip.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class MenTopWearInfo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private GeneralMenTopWeerInfo generalMenTopWeerInfo;
	
	@OneToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
}
