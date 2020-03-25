package com.flip.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Brand{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private String code;
	
	private String desc;
	
//	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE },fetch = FetchType.LAZY)
//	@JoinTable(name = "BRAND_SUB_CATEROGY", joinColumns = { @JoinColumn(name = "BRAND_ID") }, inverseJoinColumns = {
//			@JoinColumn(name = "SUB_CATEROGY_ID") })
//	//@JsonManagedReference(value="brand-sub-eference")
//	private List<SubCategory> subCategoryLst = new ArrayList<>();
	
}
