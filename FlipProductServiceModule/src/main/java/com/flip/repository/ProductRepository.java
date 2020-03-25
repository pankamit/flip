package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.Brand;
import com.flip.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

	public Product getById(String id);
	
}
