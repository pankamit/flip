package com.flip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.ProductConfig;

@Repository
public interface ProductConfigRepository extends JpaRepository<ProductConfig, Long>{
	
	public ProductConfig getById(Long id);
	
	//public Optional<String> findProductClassByCategoryCdAndSubCategoryCd(String categoryCode,String subCategoryCd);
	
	public Optional<ProductConfig> findByCategoryCdAndSubCategoryCd(String categoryCode,String subCategoryCd);
	
//	@Query(value="SELECT PRODUCT_CLASS FROM PRODUCT_CONFIG", nativeQuery = true)
//	public List<String> findProductClassQuerry();

}
