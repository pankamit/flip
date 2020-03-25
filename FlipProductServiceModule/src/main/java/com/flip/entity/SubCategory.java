package com.flip.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class SubCategory{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String code;
	
	private String desc;
	
	@ManyToOne
	@JsonBackReference(value="category-sub-reference")
	//@JsonBackReference
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@ManyToOne
//	@ManyToMany(mappedBy = "subCategoryLst",fetch = FetchType.LAZY)
	//@JsonBackReference(value="brand-sub-eference")
	//private List<Brand> brandLst = new ArrayList<>();
	private Brand brand;
}
