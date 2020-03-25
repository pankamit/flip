package com.flip.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.flip.entity.CouponInfo;
import com.flip.entity.CouponProductInfo;
import com.flip.entity.dto.CouponInfoRequestDto;
import com.flip.entity.dto.CouponProductInfoRequestDto;
import com.flip.entity.dto.CouponStatusRequestDto;
import com.flip.entity.dto.Response;
import com.flip.enumeration.CouponStatus;
import com.flip.feignclient.ProductFeignClient;
import com.flip.repository.CouponInfoRepository;
import com.flip.repository.CouponProductInfoRepository;
import com.flip.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private ProductFeignClient productFeignClient;

	@Autowired
	private CouponInfoRepository couponRepository;

	@Autowired
	private CouponProductInfoRepository couponProductInfoRepository;

	@Override
	public CouponInfo addCoupon(CouponInfoRequestDto couponInfoRequestDto) {
		
		CouponInfo couponInfo = new CouponInfo();
		BeanUtils.copyProperties(couponInfoRequestDto, couponInfo);

		for (String productId : couponInfoRequestDto.getProductIdLst()) {
			Response productEntityResponse = productFeignClient.getProductById(productId);
			if (productEntityResponse.getData() == null)
				throw new RuntimeException("Product Doesn't exist");

			CouponProductInfo couponProductInfo = new CouponProductInfo();
			couponProductInfo.setProductId(productId);

			couponInfo.getCouponProductInfoLst().add(couponProductInfoRepository.save(couponProductInfo));

		}
		return couponRepository.save(couponInfo);
	}

	@Override
	public Long removeCoupon(Long couponId) {
		
		Optional<CouponInfo> couponEntity = couponRepository.findById(couponId);
		if (!couponEntity.isPresent())
			throw new RuntimeException("Coupon Doesn't exist");
	
		couponRepository.deleteById(couponId);	
		return couponId;
	}

	@Override
	public CouponInfo findCouponByCouponId(Long couponId) {
		
		Optional<CouponInfo> couponEntity = couponRepository.findById(couponId);
		if (!couponEntity.isPresent())
			throw new RuntimeException("Coupon Doesn't exist");
		
		return couponEntity.get();
	}

	@Override
	public List<CouponInfo> findallCoupons() {
		return couponRepository.findAll();
	}

	@Override
	public CouponInfo addCouponToProduct(CouponProductInfoRequestDto couponProductInfoRequestDto) {
		
		Optional<CouponInfo> couponEntity = couponRepository.findById(couponProductInfoRequestDto.getCouponId());
		if (!couponEntity.isPresent())
			throw new RuntimeException("Coupon Doesn't exist");
		
		CouponInfo couponInfo = couponEntity.get();
		
		Response productEntityResponse = productFeignClient.getProductById(couponProductInfoRequestDto.getProductId());
		if (productEntityResponse.getData() == null)
			throw new RuntimeException("Product Doesn't exist");

		CouponProductInfo couponProductInfo = new CouponProductInfo();
		couponProductInfo.setProductId(couponProductInfoRequestDto.getProductId());

		couponInfo.getCouponProductInfoLst().add(couponProductInfoRepository.save(couponProductInfo));
		
		return couponRepository.save(couponInfo);
	}

	@Override
	public CouponInfo removeCouponFromProduct(CouponProductInfoRequestDto couponProductInfoRequestDto) {
		
		Optional<CouponInfo> couponEntity = couponRepository.findById(couponProductInfoRequestDto.getCouponId());
		if (!couponEntity.isPresent())
			throw new RuntimeException("Coupon Doesn't exist");
		
		CouponInfo couponInfo = couponEntity.get();
		
		CouponProductInfo removeCouponProductInfo = new CouponProductInfo();
		
		for(CouponProductInfo couponProductInfo : couponInfo.getCouponProductInfoLst()) {
			if(couponProductInfoRequestDto.getProductId().equals(couponProductInfo.getProductId())) {
				removeCouponProductInfo = couponProductInfo;
				break;
			}
		}
		
		couponInfo.getCouponProductInfoLst().remove(removeCouponProductInfo);
		couponProductInfoRepository.delete(removeCouponProductInfo);
		
		return couponRepository.save(couponInfo);
	}

	@Override
	public Boolean productEligibleForCoupon(CouponProductInfoRequestDto couponProductInfoRequestDto) {
		
		Optional<CouponInfo> couponEntity = couponRepository.findById(couponProductInfoRequestDto.getCouponId());
		if (!couponEntity.isPresent())
			throw new RuntimeException("Coupon Doesn't exist");
		
		CouponInfo couponInfo = couponEntity.get();
				
		for(CouponProductInfo couponProductInfo : couponInfo.getCouponProductInfoLst()) {
			if(couponProductInfoRequestDto.getProductId().equals(couponProductInfo.getProductId())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public CouponInfo updateCouponStatus(CouponStatusRequestDto couponStatusRequest) {
		
		Optional<CouponInfo> couponEntity = couponRepository.findById(couponStatusRequest.getCouponId());
		if (!couponEntity.isPresent())
			throw new RuntimeException("Coupon Doesn't exist");
		
		CouponInfo couponInfo = couponEntity.get();
		
		couponInfo.setCouponStatus(couponStatusRequest.getCouponStatus());
		
		return couponRepository.save(couponInfo);
	}

//	@Scheduled(cron = "0/15 * * * * *")
	@Override
	public void updateEffectiveCouponStatus() {
		
		System.out.println("Runs");
		List<CouponInfo> couponLst = couponRepository.findAll();
		
		couponLst.stream().filter(ind -> !(ind.getCouponStatus().equals(CouponStatus.NOT_APPLICABLE))).forEach(coupon -> checkValid(coupon));
		
		couponRepository.saveAll(couponLst);
		
	}
	
	private void checkValid(CouponInfo checkValid) {
		
		LocalDateTime currentDate = LocalDateTime.now();
		//LocalDate currentDate = LocalDate.now();
		//Date currentDate = Calendar.getInstance().getTime();

		if(currentDate.compareTo(checkValid.getValidityStartDate()) >= 0 && currentDate.compareTo(checkValid.getValidityEndDate()) <= 0) {
//		if(currentDate.compareTo(checkValid.getValidityStartDate()) >= 0 && currentDate.compareTo(checkValid.getValidityEndDate()) <= 0) {
//		if(currentDate.compareTo(checkValid.getValidityStartDate()) >= 0 && currentDate.compareTo(checkValid.getValidityEndDate()) <= 0) {
			checkValid.setCouponStatus(CouponStatus.ACTIVE);
		}else {
			checkValid.setCouponStatus(CouponStatus.EXPIRED);
		}
	}

}
