package com.flip.sharedObject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;


@Data
public class BrandSharedObject{
	
	private String name;
	
	private String code;
	
	private String desc;
	
//	private List<Long> subCategoryIdLst = new ArrayList<>();
	
}
