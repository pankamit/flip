package com.flip.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.flip.enumeration.Colour;
import com.flip.enumeration.ProductStatus;

import lombok.Data;

@Entity
@Data
public class ProductPic implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String size;
	
	private Long quantity;
	
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	
	@Enumerated(EnumType.STRING)
	private Colour picColour;
	
	@Lob
	private byte[] images;
	
	
//	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
//	@JoinTable(name = "PIC_PRODUCT", joinColumns = { @JoinColumn(name = "PIC_ID") }, inverseJoinColumns = {
//			@JoinColumn(name = "PRODUCT_ID") })
//	private List<Product> eProductLst = new ArrayList<>();

}
