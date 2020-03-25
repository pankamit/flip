package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{
	
	public Brand getById(Long id);

}
