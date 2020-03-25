package com.flip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.entity.Wishlist;
import com.flip.entity.dto.Response;
import com.flip.entity.dto.UserInfoResponseDto;
import com.flip.entity.dto.WishlistProductRequest;
import com.flip.entity.dto.WishlistRequest;
import com.flip.entity.dto.WishlistResponseDto;
import com.flip.feignclient.UserInfoFeignClient;
import com.flip.service.WishlistService;

@RestController
public class WishListController {
	
	@Autowired
	private WishlistService wishlistServie;
	
	@Autowired
	private UserInfoFeignClient userInfoFeignClient;

	@PostMapping("/wishlist/add")
	public Wishlist addWishlist(@RequestBody WishlistRequest wishlistRequest) {
		return wishlistServie.addWishlist(wishlistRequest);
	}
	
	@DeleteMapping("/wishlist/delete")
	public Long deleteWishlist(@RequestBody WishlistRequest wishlistRequest) {
		return wishlistServie.deleteWishlist(wishlistRequest);
	}
	
	@PostMapping("/wishlist/findall/{userId}")
	public List<Wishlist> getAllWishlist(@PathVariable("userId") String userId){
		return wishlistServie.getAllWishlist(userId);
	}
	
	@GetMapping("/wishlist/find")
	public WishlistResponseDto getWishlistById(@RequestBody WishlistRequest wishlistRequest) {
		return wishlistServie.getWishlistById(wishlistRequest);
	}
	
	@PutMapping("/wishlist/add")
	public Wishlist updateWishlistName(@RequestBody WishlistRequest wishlistRequest) {
		return wishlistServie.updateWishlistName(wishlistRequest);
	}
	
	@PostMapping("/wishlist/addproduct")
	public Wishlist addWishlist(@RequestBody WishlistProductRequest wishlistProductRequest) {
		return wishlistServie.addProductToWishlist(wishlistProductRequest);
	}
	
	@DeleteMapping("/wishlist/deleteproduct")
	public Wishlist deleteWishlist(@RequestBody WishlistProductRequest wishlistProductRequest) {
		return wishlistServie.deleteProductToWishlist(wishlistProductRequest);
	}
	
	@GetMapping("/hello/{wishlistId}")
	public UserInfoResponseDto getHelloWishlistById(@PathVariable("wishlistId") String wishlistId) {
		
		Response productResponse = userInfoFeignClient.helloUser();
		
		Response productEntityResponse = userInfoFeignClient.getUserById(wishlistId);
		System.out.println("After feign client call "+productEntityResponse.getData());
		UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String productJsonString = objectMapper.writeValueAsString(productEntityResponse.getData());
			
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			userInfoResponseDto = objectMapper.readValue(productJsonString, UserInfoResponseDto.class);
		System.out.println("User response desc  "+userInfoResponseDto);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userInfoResponseDto;
		
		
	}
	
}
