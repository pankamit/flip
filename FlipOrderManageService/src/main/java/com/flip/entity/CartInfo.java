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
public class CartInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userId;

	private Double amount = 0.0;
	
	private Integer productQuntity = 0;
	
	private Long couponId;
	
	private Double resultAmount = 0.0;
	
	@OneToMany
	@JoinColumn(name = "Cart_ID")
	private List<ProductInfo> cartProductInfoLst = new ArrayList<>();
}
