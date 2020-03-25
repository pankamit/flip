package com.flip.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.entity.CartInfo;
import com.flip.entity.CouponInfo;
import com.flip.entity.ProductInfo;
import com.flip.entity.dto.CartInfoResponseDto;
import com.flip.entity.dto.CartProductRequestDto;
import com.flip.entity.dto.CouponCartInfoRequestDto;
import com.flip.entity.dto.ProdutResponseDto;
import com.flip.entity.dto.Response;
import com.flip.enumeration.CouponStatus;
import com.flip.feignclient.ProductFeignClient;
import com.flip.repository.CartInfoRepository;
import com.flip.repository.CouponInfoRepository;
import com.flip.repository.ProductInfoRepository;
import com.flip.service.CartService;

@Service
public class CartServiceImp implements CartService {

	@Autowired
	private CartInfoRepository cartInfoRepository;

	@Autowired
	private ProductInfoRepository cartProductInfoRepository;

	@Autowired
	private ProductFeignClient productFeignClient;
	
	@Autowired
	private CouponInfoRepository couponInfoRepository;

	@Override
	public CartInfo addCart(String userId) {

		CartInfo cartInfo = new CartInfo();
		cartInfo.setUserId(userId);
		return cartInfoRepository.save(cartInfo);
	}

	@Override
	public Long removeCart(String userId) {

		Optional<CartInfo> cartInfoEntity = cartInfoRepository.findByUserId(userId);
		if (!cartInfoEntity.isPresent())
			throw new RuntimeException("cart doesn't exist");
		Long deletedCartId =  cartInfoEntity.get().getId();
		cartInfoRepository.delete(cartInfoEntity.get());
		return deletedCartId;
	}

	@Override
	public CartInfoResponseDto findByCartId(Long cartId) {

		Optional<CartInfo> cartInfoEntity = cartInfoRepository.findById(cartId);
		if (!cartInfoEntity.isPresent())
			throw new RuntimeException("cart doesn't exist");

		CartInfoResponseDto cartInfoResponseDto = new CartInfoResponseDto();
		BeanUtils.copyProperties(cartInfoEntity.get(), cartInfoResponseDto);

		List<String> productIdArrLst = new ArrayList<>();

		cartInfoEntity.get().getCartProductInfoLst().stream()
				.forEach(cartProduct -> productIdArrLst.add(cartProduct.getProductId()));

		Response productEntityResponse = productFeignClient.getAllProduct();

		List<ProdutResponseDto> produtResponseDtoLst = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String productJsonArrString = objectMapper.writeValueAsString(productEntityResponse.getData());
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<ProdutResponseDto> productResponseLst = objectMapper.readValue(productJsonArrString,
					new TypeReference<List<ProdutResponseDto>>() {
					});

			productResponseLst.stream().filter(product -> isElementPresent(productIdArrLst, product.getId()))
					.forEach(product -> {
						produtResponseDtoLst.add(product);
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		cartInfoResponseDto.setProductResponseDtoLst(produtResponseDtoLst);
		return cartInfoResponseDto;
	}

	private Boolean isElementPresent(List<String> strArr, String id) {
		return strArr.contains(id);
	}

	@Override
	public CartInfo addProduct(CartProductRequestDto cartProductRequest) {
		Optional<CartInfo> cartInfoEntity = cartInfoRepository.findById(cartProductRequest.getCartId());
		if (!cartInfoEntity.isPresent())
			throw new RuntimeException("cart doesn't exist");

		Response productEntityResponse = productFeignClient.getProductById(cartProductRequest.getProductId());
		if (productEntityResponse.getData() == null)
			throw new RuntimeException("Product Doesn't exist");

		CartInfo cartInfo = cartInfoEntity.get();
		ProductInfo cartProductInfo = new ProductInfo();
		cartProductInfo.setProductId(cartProductRequest.getProductId());
		cartInfo.getCartProductInfoLst().add(cartProductInfoRepository.save(cartProductInfo));

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String productJsonString = objectMapper.writeValueAsString(productEntityResponse.getData());
			JsonNode jsonNodeRoot = objectMapper.readTree(productJsonString);
			cartInfo.setAmount(cartInfo.getAmount() + jsonNodeRoot.get("price").get("actualPrice").asDouble());
			cartInfo.setProductQuntity(cartInfo.getCartProductInfoLst().size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cartInfoRepository.save(cartInfo);
	}

	@Override
	public CartInfo removeProduct(CartProductRequestDto cartProductRequest) {

		Optional<CartInfo> cartInfoEntity = cartInfoRepository.findById(cartProductRequest.getCartId());
		if (!cartInfoEntity.isPresent())
			throw new RuntimeException("cart doesn't exist");

		Response productEntityResponse = productFeignClient.getProductById(cartProductRequest.getProductId());
		if (productEntityResponse.getData() == null)
			throw new RuntimeException("Product Doesn't exist");

		CartInfo cartInfo = cartInfoEntity.get();
		ProductInfo removeCartProductInfo = new ProductInfo();

		List<ProductInfo> cartInfoProductInfoLst = cartInfo.getCartProductInfoLst();
		for (ProductInfo cartInfoProductInfo : cartInfoProductInfoLst) {
			if (cartInfoProductInfo.getProductId().equals(cartProductRequest.getProductId())) {
				removeCartProductInfo = cartInfoProductInfo;
				break;

			}
		}
		cartInfo.getCartProductInfoLst().remove(removeCartProductInfo);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String productJsonString = objectMapper.writeValueAsString(productEntityResponse.getData());
			JsonNode jsonNodeRoot = objectMapper.readTree(productJsonString);
			cartInfo.setAmount(cartInfo.getAmount() - jsonNodeRoot.get("price").get("actualPrice").asDouble());
			cartInfo.setProductQuntity(cartInfo.getCartProductInfoLst().size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		cartProductInfoRepository.delete(removeCartProductInfo);
		return cartInfoRepository.save(cartInfo);
	}

	@Override
	public CartInfo addCoupon(CouponCartInfoRequestDto couponCartInfoRequest) {

		Optional<CartInfo> cartInfoEntity = cartInfoRepository.findByUserId(couponCartInfoRequest.getUserId());
		if (!cartInfoEntity.isPresent())
			throw new RuntimeException("cart doesn't exist");

		Optional<CouponInfo> couponInfoEntity = couponInfoRepository.findByIdAndCouponStatus(couponCartInfoRequest.getCouponId(),CouponStatus.ACTIVE.name());
		if (!couponInfoEntity.isPresent())
			throw new RuntimeException("No Active Coupon Exists");

		CartInfo cartInfo = cartInfoEntity.get();
		cartInfo.setCouponId(couponCartInfoRequest.getCouponId());

		if (couponInfoEntity.get().getIsPercentageOff()) {
			cartInfo.setResultAmount(cartInfo.getAmount()
					- discountByPercentage(cartInfo.getAmount(), couponInfoEntity.get().getCouponDiscountPercent()));
		} else {
			cartInfo.setResultAmount(cartInfo.getAmount() - couponInfoEntity.get().getCouponAmount());
		}

		return cartInfoRepository.save(cartInfo);
	}

	private Double discountByPercentage(Double amount, Double percentage) {
		return amount * percentage * 0.01;
	}

	public CartInfo removeCoupon(String userId) {

		Optional<CartInfo> cartInfoEntity = cartInfoRepository.findByUserId(userId);
		if (!cartInfoEntity.isPresent())
			throw new RuntimeException("cart doesn't exist");

		CartInfo cartInfo = cartInfoEntity.get();
		cartInfo.setCouponId(null);
		cartInfo.setResultAmount(cartInfo.getAmount());
		
		return cartInfoRepository.save(cartInfo);
	}

}
