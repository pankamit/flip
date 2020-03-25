package com.flip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
	
	public List<Wishlist> findByUserId(String userId);

}
