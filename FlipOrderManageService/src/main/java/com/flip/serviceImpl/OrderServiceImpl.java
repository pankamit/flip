package com.flip.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.config.producer.PrducerOrderReqEvent;
import com.flip.config.producer.ProductRatingEventProducer;
import com.flip.entity.GeneralPic;
import com.flip.entity.OrderActivity;
import com.flip.entity.OrderInfo;
import com.flip.entity.ProductInfo;
import com.flip.entity.Rating;
import com.flip.entity.dto.OrderInfoRequestDto;
import com.flip.entity.dto.OrderInfoResponseDto;
import com.flip.entity.dto.OrderInfoUserRequestDto;
import com.flip.entity.dto.OrderStatusRequestDto;
import com.flip.entity.dto.ProdutResponseDto;
import com.flip.entity.dto.RatingInfoRequestDto;
import com.flip.entity.dto.Response;
import com.flip.event.OrderReplyEvent;
import com.flip.event.OrderRequestEvent;
import com.flip.event.ProductRatingEvent;
import com.flip.feignclient.ProductFeignClient;
import com.flip.feignclient.UserInfoFeignClient;
import com.flip.repository.GeneralPicRepository;
import com.flip.repository.OrderActivityRepository;
import com.flip.repository.OrderInfoRepository;
import com.flip.repository.ProductInfoRepository;
import com.flip.repository.RatingRepository;
import com.flip.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderInfoRepository orderInfoRepository;

	@Autowired
	private OrderActivityRepository orderActivityRepository;

	@Autowired
	private ProductInfoRepository productInfoRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private GeneralPicRepository generalPicRepository;

	@Autowired
	private UserInfoFeignClient userInfoFeignClient;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRatingEventProducer productRatingEventProducer;
	
	@Autowired
	private PrducerOrderReqEvent prducerOrderReq;

	@Override
	public OrderInfo createOrder(OrderInfoRequestDto orderInfoRequest) {

		userExists(orderInfoRequest.getUserId());

		OrderInfo orderInfo = new OrderInfo();

		BeanUtils.copyProperties(orderInfoRequest, orderInfo);

		OrderActivity orderActivity = new OrderActivity();

		orderActivity.setStatus(orderInfoRequest.getEffOrderStatus());
		orderInfo.getOrderActivityLst().add(orderActivityRepository.save(orderActivity));

		List<ProductInfo> productInfoLst = new ArrayList<>();

		for (String productId : orderInfoRequest.getProductIdLst()) {

			Response productResponse = productFeignClient.getProductById(productId);

			if (productResponse.getData() == null)
				throw new RuntimeException("Product is not present with product Id : " + productId);

			ProductInfo productInfo = new ProductInfo();
			productInfo.setProductId(productId);

			Rating rating = new Rating();
			rating.setProductId(productId);
			rating.setUserId(orderInfoRequest.getUserId());
			Rating ratingSaved = ratingRepository.save(rating);

			productInfo.setRating(ratingSaved);

			productInfoLst.add(productInfoRepository.save(productInfo));
		}

		orderInfo.setOrderProductInfoLst(productInfoLst);

		return orderInfoRepository.save(orderInfo);
	}

	private Boolean userExists(String userId) {
		Response userInfoEntity = userInfoFeignClient.getUserById(userId);

		if (userInfoEntity.getData() == null)
			throw new RuntimeException("User doesn't exist with this userId : " + userId);

		return true;
	}

	@Override
	public Long deleteOrder(OrderInfoUserRequestDto orderInfoUserRequest) {

		Optional<OrderInfo> orderInfoEntity = orderInfoRepository.findById(orderInfoUserRequest.getOrderId());

		if (!orderInfoEntity.isPresent())
			throw new RuntimeException("Order is not present with orderId : " + orderInfoUserRequest.getOrderId());

		OrderInfo orderInfo = orderInfoEntity.get();
		Long deletedOrderId = orderInfo.getId();

		orderInfoRepository.delete(orderInfo);

		return deletedOrderId;
	}

	@Override
	public List<OrderInfoResponseDto> getAllOrders(String userId) {

		List<OrderInfoResponseDto> orderInfoResponseDtoLst = new ArrayList<>();

		List<OrderInfo> orderList = orderInfoRepository.findByUserId(userId);

		List<ProdutResponseDto> produtResponseDtoLst = findAllProducts();

		for (OrderInfo order : orderList) {

			OrderInfoResponseDto orderInfoResponseDto = objectMapper.convertValue(order, OrderInfoResponseDto.class);

			orderInfoResponseDto.setOrderProductInfoLst(
					convertProdutToProductInfo(orderInfoResponseDto.getOrderProductInfoLst(), produtResponseDtoLst));
			orderInfoResponseDtoLst.add(orderInfoResponseDto);
		}

		return orderInfoResponseDtoLst;

	}

	private List<ProdutResponseDto> findAllProducts() {
		Response productEntityResponse = productFeignClient.getAllProduct();

		List<ProdutResponseDto> produtResponseDtoLst = objectMapper.convertValue(productEntityResponse.getData(),
				new TypeReference<List<ProdutResponseDto>>() {
				});

		return produtResponseDtoLst;
	}

	@Override
	public OrderInfoResponseDto getOrderById(OrderInfoUserRequestDto orderInfoUserRequest) {

		Optional<OrderInfo> orderInfoEntity = orderInfoRepository.findByUserIdAndId(orderInfoUserRequest.getUserId(),
				orderInfoUserRequest.getOrderId());

		if (!orderInfoEntity.isPresent())
			throw new RuntimeException("Order is not present with orderId : " + orderInfoUserRequest.getOrderId()
					+ " and userId : " + orderInfoUserRequest.getUserId());

		return convertOrderEntityToOrderResponse(orderInfoEntity.get());
	}

	private OrderInfoResponseDto convertOrderEntityToOrderResponse(OrderInfo orderInfo) {

		List<ProdutResponseDto> produtResponseDtoLst = findAllProducts();

		OrderInfoResponseDto orderInfoResponseDto = objectMapper.convertValue(orderInfo, OrderInfoResponseDto.class);

		orderInfoResponseDto.setOrderProductInfoLst(
				convertProdutToProductInfo(orderInfoResponseDto.getOrderProductInfoLst(), produtResponseDtoLst));

		return orderInfoResponseDto;
	}

	private List<ProdutResponseDto> convertProdutToProductInfo(List<ProdutResponseDto> actualProductLst,
			List<ProdutResponseDto> referenceLst) {

		List<String> actualProductIdLst = new ArrayList<>();

		actualProductLst.stream().forEach(actualProduct -> actualProductIdLst.add(actualProduct.getId()));

		return referenceLst.stream().filter(product -> actualProductLst.contains(product.getId()))
				.collect(Collectors.toList());

	}

	@Override
	public OrderInfoResponseDto updateOrderStatus(OrderStatusRequestDto orderStatusRequest) {

		Optional<OrderInfo> orderInfoEntity = orderInfoRepository.findByUserIdAndId(orderStatusRequest.getUserId(),
				orderStatusRequest.getOrderId());

		if (!orderInfoEntity.isPresent())
			throw new RuntimeException("Order is not present with orderId : " + orderStatusRequest.getOrderId()
					+ " and userId : " + orderStatusRequest.getUserId());

		OrderInfo orderInfo = orderInfoEntity.get();
		orderInfo.setEffOrderStatus(orderStatusRequest.getStatus());
		orderInfo.setEffOrderDate(Calendar.getInstance().getTime());

		OrderActivity orderActivity = new OrderActivity();

		orderActivity.setStatus(orderStatusRequest.getStatus());
		orderActivity.setOrderDate(Calendar.getInstance().getTime());
		orderActivity.setActivityComment(orderStatusRequest.getActivityComment());

		orderInfo.getOrderActivityLst().add(orderActivityRepository.save(orderActivity));

		OrderInfo orderInfoSaved = orderInfoRepository.save(orderInfo);

		return convertOrderEntityToOrderResponse(orderInfoSaved);
	}

	@Override
	public Rating updateRating(RatingInfoRequestDto ratingInfoRequest, MultipartFile[] files) {

		Optional<Rating> ratingEntity = ratingRepository.findByIdAndProductIdAndUserId(ratingInfoRequest.getRatingId(),
				ratingInfoRequest.getProductId(), ratingInfoRequest.getUserId());

		if (!ratingEntity.isPresent())
			throw new RuntimeException("Rating is not Present");

		Rating rating = ratingEntity.get();

		BeanUtils.copyProperties(ratingInfoRequest, rating);

		for (MultipartFile file : files) {
			try {
				rating.getGeneralPicLst().add(generalPicRepository.save(new GeneralPic(file.getBytes())));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (ratingInfoRequest.getDeletedFileIds() != null) {
			for (Long fileId : ratingInfoRequest.getDeletedFileIds()) {
				Optional<GeneralPic> generalPicEntity = generalPicRepository.findById(fileId);

				if (generalPicEntity.isPresent()) {
					rating.getGeneralPicLst().remove(generalPicEntity.get());
					generalPicRepository.delete(generalPicEntity.get());
				}

			}
		}

		Rating savedRating = ratingRepository.save(rating);

		if (savedRating.getRatingPoint() != null) {
			ProductRatingEvent productRatingEvent = new ProductRatingEvent();
			productRatingEvent.setProductId(rating.getProductId());
			productRatingEvent.setUserId(rating.getUserId());
			productRatingEvent.setRatingId(rating.getId());
			productRatingEvent.setRatingPoint(rating.getRatingPoint());
			productRatingEventProducer.sendMessage(productRatingEvent);
		}

		return savedRating;
	}

	@Override
	public Rating resetRatingPoint(Long ratingId) {

		Optional<Rating> ratingEntity = ratingRepository.findById(ratingId);

		if (!ratingEntity.isPresent())
			throw new RuntimeException("Rating is not Present");

		Rating rating = ratingEntity.get();

		rating.setRatingPoint(null);

		Rating savedRating = ratingRepository.save(rating);

		ProductRatingEvent productRatingEvent = new ProductRatingEvent();
		productRatingEvent.setProductId(rating.getProductId());
		productRatingEvent.setUserId(rating.getUserId());
		productRatingEvent.setRatingId(rating.getId());
		productRatingEvent.setRatingPoint(null);
		productRatingEventProducer.sendMessage(productRatingEvent);

		return savedRating;
	}

	@Override
	public Rating deleteReviewComment(Long ratingId) {

		Optional<Rating> ratingEntity = ratingRepository.findById(ratingId);

		if (!ratingEntity.isPresent())
			throw new RuntimeException("Rating is not Present");

		Rating rating = ratingEntity.get();

		rating.setReviewComment(null);
		rating.setReviewTitle(null);

		return ratingRepository.save(rating);
	}

	@Override
	public OrderReplyEvent productOrderEvent(OrderRequestEvent orderRequestEvent) throws Exception {
		return prducerOrderReq.senndMessage(orderRequestEvent);
	}

}
