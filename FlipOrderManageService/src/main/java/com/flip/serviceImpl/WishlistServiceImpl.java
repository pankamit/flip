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
import com.flip.entity.Wishlist;
import com.flip.entity.WishlistProductInfo;
import com.flip.entity.dto.ProdutResponseDto;
import com.flip.entity.dto.Response;
import com.flip.entity.dto.WishlistProductRequest;
import com.flip.entity.dto.WishlistRequest;
import com.flip.entity.dto.WishlistResponseDto;
import com.flip.feignclient.ProductFeignClient;
import com.flip.feignclient.UserInfoFeignClient;
import com.flip.repository.WishlistProductInfoRepository;
import com.flip.repository.WishlistRepository;
import com.flip.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private WishlistProductInfoRepository wishlistProductInfoRepository;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Autowired
	private UserInfoFeignClient userInfoFeignClient;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Wishlist addWishlist(WishlistRequest wishlistRequest) {

		Wishlist wishlist = new Wishlist();
		
		userExists(wishlistRequest.getUserId());
		wishlist.setUserId(wishlistRequest.getUserId());
		wishlist.setWishlistName(wishlistRequest.getWishlistName());
		wishlist.setAmount(0.0);
		wishlist.setProductQuntity(0);
		
		return wishlistRepository.save(wishlist);
	}

	@Override
	public Long deleteWishlist(WishlistRequest wishlistRequest) {
		
		userExists(wishlistRequest.getUserId());
				
		Optional<Wishlist> wishlistEntity = wishlistRepository.findById(wishlistRequest.getWishlistId());
		if (!wishlistEntity.isPresent()) {
			throw new RuntimeException("wishlist doesn't exist");
		}
		Long deletedWishlistId =  wishlistEntity.get().getId();
		wishlistRepository.delete(wishlistEntity.get());
		return deletedWishlistId;
	}

	@Override
	public List<Wishlist> getAllWishlist(String userId) {
		return wishlistRepository.findByUserId(userId);
	}

	@Override
	public WishlistResponseDto getWishlistById(WishlistRequest wishlistRequest) {
		
		userExists(wishlistRequest.getUserId());
		
		Optional<Wishlist> wishlistEntity = wishlistRepository.findById(wishlistRequest.getWishlistId());
		if (!wishlistEntity.isPresent()) 
			throw new RuntimeException("wishlist doesn't exist");
		
		WishlistResponseDto wishlistResponseDto = new WishlistResponseDto();
		BeanUtils.copyProperties(wishlistEntity.get(),wishlistResponseDto);

		List<String> productIdArrLst = new ArrayList<>();
		
		wishlistEntity.get().getWishlistProductInfoLst().stream().forEach(wishProduct -> productIdArrLst.add(wishProduct.getProductId()));
		
		Response productEntityResponse = productFeignClient.getAllProduct();

		List<ProdutResponseDto> produtResponseDtoLst = new ArrayList<>();

	
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
		
		wishlistResponseDto.setProductResponseDtoLst(produtResponseDtoLst);
		return wishlistResponseDto;
	}

	private Boolean isElementPresent(List<String> strArr, String id) {		
		return strArr.contains(id);
	}

	@Override
	public Wishlist updateWishlistName(WishlistRequest wishlistRequest) {
		Optional<Wishlist> wishlistEntity = wishlistRepository.findById(wishlistRequest.getWishlistId());
		if (!wishlistEntity.isPresent()) {
			throw new RuntimeException("wishlist doesn't exist");
		}
		Wishlist wishlist = wishlistEntity.get();
		wishlist.setWishlistName(wishlistRequest.getWishlistName());
		return wishlistRepository.save(wishlist);
	}

	@Override
	public Wishlist addProductToWishlist(WishlistProductRequest wishlistProductRequest) {
		Optional<Wishlist> wishlistEntity = wishlistRepository.findById(wishlistProductRequest.getWishlistId());
		if (!wishlistEntity.isPresent())
			throw new RuntimeException("wishlist doesn't exist");
		
		Response productEntityResponse = productFeignClient.getProductById(wishlistProductRequest.getProductId());
		if(productEntityResponse.getData() == null)
			throw new RuntimeException("Product Doesn't exist");
		
		Wishlist wishlist = wishlistEntity.get();
		WishlistProductInfo wishlistProductInfo = new WishlistProductInfo();
		wishlistProductInfo.setProductId(wishlistProductRequest.getProductId());
		WishlistProductInfo wishlistProductInfoEntity = wishlistProductInfoRepository.save(wishlistProductInfo);
		wishlist.getWishlistProductInfoLst().add(wishlistProductInfoEntity);
		
		try {
			String productJsonString = objectMapper.writeValueAsString(productEntityResponse.getData());
			JsonNode jsonNodeRoot = objectMapper.readTree(productJsonString);
			wishlist.setAmount(wishlist.getAmount() + jsonNodeRoot.get("price").get("actualPrice").asDouble());
			wishlist.setProductQuntity(wishlist.getWishlistProductInfoLst().size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wishlistRepository.save(wishlist);
	}

	@Override
	public Wishlist deleteProductToWishlist(WishlistProductRequest wishlistProductRequest) {
		Optional<Wishlist> wishlistEntity = wishlistRepository.findById(wishlistProductRequest.getWishlistId());
		if (!wishlistEntity.isPresent())
			throw new RuntimeException("wishlist doesn't exist");
		
		Response productEntityResponse = productFeignClient.getProductById(wishlistProductRequest.getProductId());
		if(productEntityResponse.getData() == null)
			throw new RuntimeException("Product Doesn't exist");
		
		WishlistProductInfo removeWishlistProductInfo = new WishlistProductInfo();
		Wishlist wishlist = wishlistEntity.get();
		List<WishlistProductInfo> wishlistProductInfoLst = wishlist.getWishlistProductInfoLst();
		for (WishlistProductInfo wishlistProductInfo : wishlistProductInfoLst) {
			if (wishlistProductInfo.getProductId().equals(wishlistProductRequest.getProductId())) {
				removeWishlistProductInfo = wishlistProductInfo;
				break;

			}
		}
		wishlist.getWishlistProductInfoLst().remove(removeWishlistProductInfo);

		try {
			String productJsonString = objectMapper.writeValueAsString(productEntityResponse.getData());
			JsonNode jsonNodeRoot = objectMapper.readTree(productJsonString);
			wishlist.setAmount(wishlist.getAmount() - jsonNodeRoot.get("price").get("actualPrice").asDouble());
			wishlist.setProductQuntity(wishlist.getWishlistProductInfoLst().size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		wishlistProductInfoRepository.delete(removeWishlistProductInfo);		
		return wishlistRepository.save(wishlist);
	}
	
	private Boolean userExists(String userId) {
		Response userInfoEntity = userInfoFeignClient.getUserById(userId);

		if (userInfoEntity.getData() == null)
			throw new RuntimeException("User doesn't exist with this userId : " + userId);

		return true;
	}

}
