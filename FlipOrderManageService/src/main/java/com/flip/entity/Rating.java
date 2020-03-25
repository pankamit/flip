package com.flip.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Rating{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long ratingPoint;
	
	private String reviewComment;
	
	private String reviewTitle;
	
	@NotNull
	private String productId;
	
	@NotNull
	private String userId;
	
	@OneToMany
	@JoinColumn(name = "PIC_ID")
	private List<GeneralPic> generalPicLst = new ArrayList<>();

}
