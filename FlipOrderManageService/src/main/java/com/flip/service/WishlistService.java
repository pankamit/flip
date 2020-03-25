package com.flip.service;

import java.util.List;

import com.flip.entity.Wishlist;
import com.flip.entity.dto.WishlistProductRequest;
import com.flip.entity.dto.WishlistRequest;
import com.flip.entity.dto.WishlistResponseDto;

public interface WishlistService {
	
	public Wishlist addWishlist(WishlistRequest wishlistRequest);
	
	public Long deleteWishlist(WishlistRequest wishlistRequest);
	
	public List<Wishlist> getAllWishlist(String userId);
	
	public WishlistResponseDto getWishlistById(WishlistRequest wishlistRequest);
	
	public Wishlist updateWishlistName(WishlistRequest wishlistRequest);

	public Wishlist addProductToWishlist(WishlistProductRequest wishlistProductRequest);

	public Wishlist deleteProductToWishlist(WishlistProductRequest wishlistProductRequest);

}
