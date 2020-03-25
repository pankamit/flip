package com.flip.entity.dto;

import java.util.Date;
import java.util.List;

import com.flip.enumeration.OrderStatus;

import lombok.Data;

@Data
public class OrderInfoResponseDto {

	private Long id;

	private OrderStatus effOrderStatus;

	private String userId;

	private Double amount;

	private Integer quantity;

	private Date effOrderDate;

	private List<ProdutResponseDto> orderProductInfoLst;

	private List<OrderActivityDto> orderActivityLst;

}
