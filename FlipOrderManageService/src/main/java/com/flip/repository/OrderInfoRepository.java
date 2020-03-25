package com.flip.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.OrderInfo;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long>{

	public List<OrderInfo> findByUserId(String userId);
	
	public Optional<OrderInfo> findByUserIdAndId(String userId,Long id);
	
}
