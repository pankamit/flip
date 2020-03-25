package com.flip.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flip.entity.Category;
import com.flip.repository.CategoryRepository;
import com.flip.service.CategoryService;
import com.flip.sharedObject.CategorySharedObject;
import com.flip.transformer.CategorySharedObjectEntityTransformer;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository cateogoryRepository;
	
	@Autowired
	private SubCategoryServiceImpl subCategoryServiceImpl;
	
	@Override
	public Category addCategory(CategorySharedObject cateogorySharedObject) {
		
		Category cateogory = CategorySharedObjectEntityTransformer.apply(cateogorySharedObject);
		Category categoryEntity = cateogoryRepository.save(cateogory);
		
		subCategoryServiceImpl.addSubCategoryList(cateogorySharedObject.getSubCategorySharedObjectLst(), categoryEntity);	
		
		return categoryEntity;
		
	}

	@Override
	public Category getCategoryById(Long id) {

		return cateogoryRepository.getById(id);
	}

	@Override
	public List<Category> getAllCategory() {

		return cateogoryRepository.findAll();
	}

	@Override
	public Category updateCategory(Long id, Category cateogory){

		if (cateogory.getId() != null) {
			if (id != cateogory.getId()) {
				throw new RuntimeException("input request is not correct");
			}
		}

		return cateogoryRepository.save(cateogory);
	}

	@Override
	public Category deleteCategoryById(Long id){

		Optional<Category> cateogoryEntity = cateogoryRepository.findById(id);

		if (!cateogoryEntity.isPresent()) {
			throw new RuntimeException("Category is not Available");
		}
		cateogoryRepository.deleteById(id);

		return cateogoryEntity.get();
	}

}
