package com.flip.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flip.entity.Brand;
import com.flip.entity.Category;
import com.flip.entity.ProductConfig;
import com.flip.entity.SubCategory;
import com.flip.repository.BrandRepository;
import com.flip.repository.CategoryRepository;
import com.flip.repository.ProductConfigRepository;
import com.flip.repository.SubCategoryRepository;
import com.flip.service.SubCategoryService;
import com.flip.sharedObject.SubCategorySharedObject;
import com.flip.transformer.SubCategorySharedObjectEntityTransformer;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private ProductConfigRepository productConfigRepository;

	@Override
	public SubCategory addSubCategory(SubCategorySharedObject subCategorySharedObject) {
		SubCategory subCategory = SubCategorySharedObjectEntityTransformer.apply(subCategorySharedObject);
		Optional<Category> categoryOptional = categoryRepository.findById(subCategorySharedObject.getCategoryId());
		if (!categoryOptional.isPresent())
			throw new RuntimeException("Category is not present");

		subCategory.setCategory(categoryOptional.get());

		Optional<Brand> brandOptional = brandRepository.findById(subCategorySharedObject.getBrandId());
		if (!brandOptional.isPresent())
			throw new RuntimeException("Brand is not present");

		subCategory.setBrand(brandOptional.get());
		
		Optional<ProductConfig> productConfigEntity = productConfigRepository
				.findByCategoryCdAndSubCategoryCd(categoryOptional.get().getCode(), subCategorySharedObject.getCode());
		if (!productConfigEntity.isPresent())
			throw new RuntimeException("Category - SubCategory Combination is not valid");

		return subCategoryRepository.save(subCategory);
	}

	@Override
	public SubCategory getSubCategoryById(Long id) {

		return subCategoryRepository.getById(id);
	}

	@Override
	public List<SubCategory> getAllSubCategory() {

		return subCategoryRepository.findAll();
	}

	@Override
	public SubCategory updateSubCategory(Long id, SubCategory subCategory) {

		if (subCategory.getId() != null) {
			if (id != subCategory.getId()) {
				throw new RuntimeException("Input request value is not correct");
			}
		}

		return subCategoryRepository.save(subCategory);
	}

	@Override
	public SubCategory deleteSubCategoryById(Long id) {

		Optional<SubCategory> subCategoryEntity = subCategoryRepository.findById(id);

		if (!subCategoryEntity.isPresent()) {
			throw new RuntimeException("SubCategory is not Available");
		}
		subCategoryRepository.deleteById(id);

		return subCategoryEntity.get();
	}

	// for Saving subCategory while saving category
	@Override
	public List<SubCategory> addSubCategoryList(List<SubCategorySharedObject> subCategorySharedObjectLst,
			Category category) {

		List<SubCategory> subCategoryLst = new ArrayList<>();

		for (SubCategorySharedObject subCategorySharedObject : subCategorySharedObjectLst) {
			SubCategory subCategory = SubCategorySharedObjectEntityTransformer.apply(subCategorySharedObject);
			subCategory.setCategory(category);

			Optional<Brand> brandOptional = brandRepository.findById(subCategorySharedObject.getBrandId());
			if (!brandOptional.isPresent())
				throw new RuntimeException("Brand is not present");

			subCategory.setBrand(brandOptional.get());

			Optional<ProductConfig> productConfigEntity = productConfigRepository
					.findByCategoryCdAndSubCategoryCd(category.getCode(), subCategorySharedObject.getCode());
			if (!productConfigEntity.isPresent())
				throw new RuntimeException("Category - SubCategory Combination is not valid");

			subCategoryLst.add(subCategoryRepository.save(subCategory));
		}
		return subCategoryLst;

	}

}
