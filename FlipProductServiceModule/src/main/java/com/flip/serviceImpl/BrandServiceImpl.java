package com.flip.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.flip.entity.Brand;
import com.flip.repository.BrandRepository;
import com.flip.service.BrandService;
import com.flip.sharedObject.BrandSharedObject;
import com.flip.transformer.BrandSharedObjectEntityTransformer;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository brandRepository;
	
	@Override
	public Brand addBrand(BrandSharedObject brandSharedObject) {
		Brand brand = BrandSharedObjectEntityTransformer.apply(brandSharedObject);
		return brandRepository.save(brand);
	}

	@Override
	public Brand getBrandById(Long id) {

		return brandRepository.getById(id);
	}

	@Override
	public List<Brand> getAllBrand() {

		return brandRepository.findAll();
	}

	@Override
	public Brand updateBrand(Long id, Brand brand) throws Exception {

		if (brand.getId() != null) {
			if (id != brand.getId()) {
				throw new Exception("input request is not correct");
			}
		}

		return brandRepository.save(brand);
	}

	@Override
	public Brand deleteBrandById(Long id) throws Exception {

		Optional<Brand> brandEntity = brandRepository.findById(id);

		if (!brandEntity.isPresent()) {
			throw new Exception("Brand is not Available");
		}
		brandRepository.deleteById(id);

		return brandEntity.get();
	}

}
