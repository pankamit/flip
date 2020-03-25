package com.flip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.CouponInfo;

@Repository
public interface CouponInfoRepository extends JpaRepository<CouponInfo, Long>{
	
	Optional<CouponInfo> findByIdAndCouponStatus(Long couponId,String status);

}
