package com.flip.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.flip.enumeration.OrderStatus;

import lombok.Data;

@Entity
@Data
public class OrderActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.PLACED;
			
	private Date orderDate = Calendar.getInstance().getTime();

	private String activityComment;

}

