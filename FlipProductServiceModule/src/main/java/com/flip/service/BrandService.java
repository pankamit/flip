package com.flip.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flip.entity.Brand;
import com.flip.sharedObject.BrandSharedObject;


public interface BrandService {

	public Brand addBrand(BrandSharedObject brand);
	
	public Brand getBrandById(Long id);
	
	public List<Brand> getAllBrand();
	
	public Brand updateBrand(Long id,Brand brand) throws Exception;
	
	public Brand deleteBrandById(Long id) throws Exception;
	
}
