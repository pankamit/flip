package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.WishlistProductInfo;

@Repository
public interface WishlistProductInfoRepository extends JpaRepository<WishlistProductInfo, Long>{

}
