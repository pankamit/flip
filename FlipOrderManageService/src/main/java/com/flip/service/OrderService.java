package com.flip.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.flip.entity.OrderInfo;
import com.flip.entity.Rating;
import com.flip.entity.dto.OrderInfoRequestDto;
import com.flip.entity.dto.OrderInfoResponseDto;
import com.flip.entity.dto.OrderInfoUserRequestDto;
import com.flip.entity.dto.OrderStatusRequestDto;
import com.flip.entity.dto.RatingInfoRequestDto;
import com.flip.event.OrderReplyEvent;
import com.flip.event.OrderRequestEvent;

public interface OrderService {
	
	public OrderInfo createOrder(OrderInfoRequestDto orderInfoRequest);
	
	public Long deleteOrder(OrderInfoUserRequestDto orderInfoUserRequest);
	
	public List<OrderInfoResponseDto> getAllOrders(String userId);
	
	public OrderInfoResponseDto getOrderById(OrderInfoUserRequestDto orderInfoUserRequest);
	
	public OrderInfoResponseDto updateOrderStatus(OrderStatusRequestDto orderStatusRequest);
	
	public Rating updateRating(RatingInfoRequestDto ratingInfoRequest,MultipartFile[] files);
	
	public Rating resetRatingPoint(Long ratingId);
	
	public Rating deleteReviewComment(Long ratingId);
	
	public OrderReplyEvent productOrderEvent(OrderRequestEvent orderRequestEvent) throws Exception;
	
	
}
