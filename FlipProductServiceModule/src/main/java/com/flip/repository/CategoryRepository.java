package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	public Category getById(Long id);
	
}
