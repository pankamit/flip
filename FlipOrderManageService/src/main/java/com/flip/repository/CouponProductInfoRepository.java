package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.CouponProductInfo;

@Repository
public interface CouponProductInfoRepository extends JpaRepository<CouponProductInfo, Long>{

}
