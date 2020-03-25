package com.flip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.CartInfo;

@Repository
public interface CartInfoRepository extends JpaRepository<CartInfo, Long>{
	
	public Optional<CartInfo> findByUserId(String userId);

}
