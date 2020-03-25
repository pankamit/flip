package com.flip.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Wishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String wishlistName;
	
	private String userId;

	private Double amount;
	
	private Integer productQuntity;
	
	@OneToMany
	@JoinColumn(name = "WISHLIST_ID")
	private List<WishlistProductInfo> wishlistProductInfoLst = new ArrayList<>(); 
	
}
