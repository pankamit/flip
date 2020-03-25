package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.ProductPic;

@Repository
public interface ProductPicRepository extends JpaRepository<ProductPic, Long>{
	
	public ProductPic getById(Long id);

}
