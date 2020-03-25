package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flip.entity.dto.OrderInfoRequestDto;
import com.flip.entity.dto.OrderInfoUserRequestDto;
import com.flip.entity.dto.OrderStatusRequestDto;
import com.flip.entity.dto.RatingInfoRequestDto;
import com.flip.entity.dto.Response;
import com.flip.event.OrderRequestEvent;
import com.flip.service.OrderService;

@Configuration
@RestController
public class OrderController {

	@Value("${hello.name}")
	private String helloName;
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public Response createOrder(@RequestBody OrderInfoRequestDto orderInfoRequest) {	
		return Response.builder().data(orderService.createOrder(orderInfoRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/order")
	public Response deleteOrder(@RequestBody OrderInfoUserRequestDto orderInfoUserRequest) {	
		return Response.builder().data(orderService.deleteOrder(orderInfoUserRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("/order/allorder/{userId}")
	public Response getAllOrders(@PathVariable("userId") @RequestBody String userId) {	
		return Response.builder().data(orderService.getAllOrders(userId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("/order/orderbyid")
	public Response getOrderById(@RequestBody OrderInfoUserRequestDto orderInfoUserRequest) {	
		return Response.builder().data(orderService.getOrderById(orderInfoUserRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping("/order/updatestatus")
	public Response updateOrderStatus(@RequestBody OrderStatusRequestDto orderStatusRequest) {	
		return Response.builder().data(orderService.updateOrderStatus(orderStatusRequest)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping(value = "/order/updaterating",consumes = {"multipart/form-data"})
	public Response updateRating(@RequestPart("ratingInfoRequest") RatingInfoRequestDto ratingInfoRequest,@RequestPart MultipartFile[] files) {	
		return Response.builder().data(orderService.updateRating(ratingInfoRequest,files)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping("/order/resetrating/{ratingId}")
	public Response resetRatingPoint(@PathVariable("ratingId")  Long ratingId) {	
		return Response.builder().data(orderService.resetRatingPoint(ratingId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping("/order/resetreview/{ratingId}")
	public Response resetReviewComments(@PathVariable("ratingId")  Long ratingId) {	
		return Response.builder().data(orderService.deleteReviewComment(ratingId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PostMapping("/order/request")
	public Response produceOrderRequest(@RequestBody OrderRequestEvent orderRequstEvent) throws Exception{	
		return Response.builder().data(orderService.productOrderEvent(orderRequstEvent)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("/hello")
	public String helloNameDisplay() {
		return helloName;
	}
	
	
	
}
