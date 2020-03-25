package com.flip.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.joda.time.LocalDate;

import com.flip.enumeration.OrderStatus;

import lombok.Data;

@Entity
@Data
public class OrderInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus effOrderStatus = OrderStatus.PLACED;
	
	private String userId;
	
	private Double amount;
	
	private Integer quantity;
	
	private Date effOrderDate = Calendar.getInstance().getTime();
	
	@OneToMany
	@JoinColumn(name = "ORDER_ID")
	private List<ProductInfo> orderProductInfoLst = new ArrayList<>();
	
	@OneToMany
	@JoinColumn(name = "ORDER_ID")
	private List<OrderActivity> orderActivityLst = new ArrayList<>();
}

