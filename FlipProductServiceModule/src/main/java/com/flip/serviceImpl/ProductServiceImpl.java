package com.flip.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.flip.entity.Brand;
import com.flip.entity.Category;
import com.flip.entity.MenFootWearInfo;
import com.flip.entity.Product;
import com.flip.entity.ProductConfig;
import com.flip.entity.ProductPic;
import com.flip.entity.Rating;
import com.flip.entity.SubCategory;
import com.flip.event.ProductRatingEvent;
import com.flip.generator.IdGenerator;
import com.flip.repository.BrandRepository;
import com.flip.repository.CategoryRepository;
import com.flip.repository.MenFootWearInfoRepository;
import com.flip.repository.ProductConfigRepository;
import com.flip.repository.ProductPicRepository;
import com.flip.repository.ProductRepository;
import com.flip.repository.RatingRepository;
import com.flip.repository.SubCategoryRepository;
import com.flip.service.ProductService;
import com.flip.sharedObject.GeneralMenFootWearInfoSharedObject;
import com.flip.sharedObject.ProductPicSharedObject;
import com.flip.sharedObject.ProductSharedObject;
import com.flip.transformer.GeneralMenFootWearSharedObjectEntityTransformer;
import com.flip.transformer.ProductSharedObjectEntityTransformer;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private ProductPicRepository productPicRepository;

	@Autowired
	private ProductConfigRepository productConfigRepository;

	@Autowired
	private MenFootWearInfoRepository menFootWearInfoRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public Product addProduct(ProductSharedObject productSharedObject, Long[] picIdLst) {

		Product product = ProductSharedObjectEntityTransformer.apply(productSharedObject);

		Optional<Category> categoryOptional = categoryRepository.findById(productSharedObject.getCategoryId());
		if (!categoryOptional.isPresent())
			throw new RuntimeException("Category is not present");

		product.setCategory(categoryOptional.get());

		Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(productSharedObject.getCategoryId());
		if (!subCategoryOptional.isPresent())
			throw new RuntimeException("Sub Category is not present");

		product.setSubCategory(subCategoryOptional.get());

		Optional<Brand> brandOptional = brandRepository.findById(productSharedObject.getBrandId());
		if (!brandOptional.isPresent())
			throw new RuntimeException("Brand is not present");

		product.setBrand(brandOptional.get());

		Optional<ProductConfig> productConfigEntity = productConfigRepository
				.findByCategoryCdAndSubCategoryCd(product.getCategory().getCode(), product.getSubCategory().getCode());

		if (!productConfigEntity.isPresent()) {
			throw new RuntimeException("Category - SubCategory Combination is not valid");
		}

		List<ProductPic> productPicLst = new ArrayList<>();
//		if (files.length == productSharedObject.getPicLst().size()) {
//			for (int ind = 0; ind < files.length; ind++) {
//				ProductPic productPic = new ProductPic();
//				try {
//					productPic.setImages(files[ind].getBytes());
//				} catch (IOException e) {
//					throw new RuntimeException("Input output Exception");
//				}
//				productPic.setPicColour(productSharedObject.getPicLst().get(ind).getPicColour());
//				productPic.setSize(productSharedObject.getPicLst().get(ind).getSize());
//				productPic.setStatus(productSharedObject.getPicLst().get(ind).getStatus());
//
//				productPicLst.add(productPicRepository.save(productPic));
//			}
//		}

		for (int ind = 0; ind < picIdLst.length; ind++) {
			Optional<ProductPic> productPicEntity = productPicRepository.findById(picIdLst[ind]);
			if (!productPicEntity.isPresent())
				throw new RuntimeException("Product Pic is not present with picId : " + picIdLst[ind]);
			productPicLst.add(productPicEntity.get());
		}

		product.setPicLst(productPicLst);

		IdGenerator.setBrandCode(product.getBrand().getCode());
		IdGenerator.setBrandId(product.getBrand().getId());
		IdGenerator.setCategoryCode(product.getCategory().getCode());
		IdGenerator.setCategoryId(product.getCategory().getId());
		IdGenerator.setSubCategoryCode(product.getSubCategory().getCode());
		IdGenerator.setSubCategoryId(product.getSubCategory().getId());

		return productRepository.save(product);
	}

	@Override
	public Product getProductById(String id) {

		return productRepository.getById(id);
	}

	@Override
	public List<Product> getAllProduct() {

		return productRepository.findAll();
	}

	@Override
	public Product updateProduct(String id, Product product) throws Exception {

		if (product.getId() != null) {
			if (id != product.getId()) {
				throw new Exception("input request is not correct");
			}
		}

		return productRepository.save(product);
	}

	@Override
	public Product deleteProductById(String id) throws Exception {

		Optional<Product> productEntity = productRepository.findById(id);

		if (!productEntity.isPresent())
			throw new Exception("Product is not Available");

		productRepository.deleteById(id);

		return productEntity.get();
	}

	public ProductPic addProductPic(MultipartFile file, ProductPicSharedObject picInfoSharedObject) {

		ProductPic productPic = new ProductPic();

		try {
			productPic.setImages(file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Input output Exception");
		}
		productPic.setPicColour(picInfoSharedObject.getPicColour());
		productPic.setSize(picInfoSharedObject.getSize());
		productPic.setStatus(picInfoSharedObject.getStatus());

		return productPicRepository.save(productPic);
	}

	public Product addProductPicLst(MultipartFile[] file, ProductPicSharedObject[] picInfoSharedObject,
			String productId) {

		Optional<Product> productEntity = productRepository.findById(productId);

		if (!productEntity.isPresent())
			throw new RuntimeException("product is not present");

		Product product = productEntity.get();

		List<ProductPic> productPicLst = new ArrayList<>();

		for (int ind = 0; ind < file.length; ind++) {
			ProductPic productPic = new ProductPic();
			try {
				productPic.setImages(file[ind].getBytes());
			} catch (IOException e) {
				throw new RuntimeException("Input output Exception");
			}
			productPic.setPicColour(picInfoSharedObject[ind].getPicColour());
			productPic.setSize(picInfoSharedObject[ind].getSize());
			productPic.setStatus(picInfoSharedObject[ind].getStatus());

			productPicLst.add(productPicRepository.save(productPic));
		}

		product.setPicLst(productPicLst);
		return productRepository.save(product);
	}

	public List<ProductConfig> getAllProductNames() {
		return productConfigRepository.findAll();
	}

	@Override
	public Product addGeneralMenFootWeerInfo(GeneralMenFootWearInfoSharedObject generalMenFootWeerInfoSharedObject,
			Product productEntity) {

		MenFootWearInfo menFootWearInfo = new MenFootWearInfo();

		menFootWearInfo.setProduct(productEntity);

		menFootWearInfo.setGeneralMenFootWeerInfo(
				GeneralMenFootWearSharedObjectEntityTransformer.apply(generalMenFootWeerInfoSharedObject));

		menFootWearInfoRepository.save(menFootWearInfo);

		return productEntity;
	}

	@Override
	public Product addPicLstToProduct(Long[] picIdLst, String productId) {

		Optional<Product> productEntity = productRepository.findById(productId);
		if (!productEntity.isPresent())
			throw new RuntimeException("product is not present");

		List<ProductPic> productPicLst = productPicRepository.findAll();

		productPicLst.stream().filter(ind -> isElementPresent(picIdLst, ind.getId()))
				.forEach(ind2 -> productEntity.get().getPicLst().add(ind2));

		return null;
	}

	private Boolean isElementPresent(Long[] arr, Long id) {
		for (int ind3 = 0; ind3 < arr.length; ind3++) {
			if (arr[ind3] == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Product updateRating(ProductRatingEvent productRatingEvent) {

		Optional<Product> productEntity = productRepository.findById(productRatingEvent.getProductId());

		if (!productEntity.isPresent())
			throw new RuntimeException("Product is not present with productId : " + productRatingEvent.getProductId());

		Product product = productEntity.get();
		
		Optional<Rating> ratingEntity = ratingRepository.findByRatingIdAndUserId(productRatingEvent.getRatingId(),
				productRatingEvent.getUserId());

		if (ratingEntity.isPresent()) {
			Rating rating = ratingEntity.get();
			product.getRatingLst().remove(rating);
			Long removedRatingPoint = rating.getRatingPoint();
			
			
			BeanUtils.copyProperties(productRatingEvent, rating);
			if(rating.getRatingPoint() == null) {
				rating.setIsRated(false);
			}else {
				rating.setIsRated(true);
			}
			Rating savedRating = ratingRepository.save(rating);
			product.getRatingLst().add(savedRating);
			
			if(removedRatingPoint.compareTo(rating.getRatingPoint()) != 0) {
				List<Rating> validRatingLst = CalculateNoOFValidRatings(product.getRatingLst());
				product.setAvgRatingPoints(calculateAvg(validRatingLst));
				product.setNoOfRatings(validRatingLst.size());
			}
			
		}else {
			Rating rating = new Rating();
			BeanUtils.copyProperties(productRatingEvent, rating);
			if(rating.getRatingPoint() == null) {
				rating.setIsRated(false);
			}else {
				rating.setIsRated(true);
			}
			Rating savedRating = ratingRepository.save(rating);
			product.getRatingLst().add(savedRating);
			List<Rating> validRatingLst = CalculateNoOFValidRatings(product.getRatingLst());
			product.setAvgRatingPoints(calculateAvg(validRatingLst));
			product.setNoOfRatings(validRatingLst.size());
		}

		return productRepository.save(product);
	}
	
	private List<Rating> CalculateNoOFValidRatings(List<Rating> ratingLst) {
		 return ratingLst.stream().filter(record -> record.getIsRated()).collect(Collectors.toList());
	}
	
	private Double calculateAvg(List<Rating> ratingLst) {
		Double sum = 0.0;
		
		for(Rating ratedRating:ratingLst) {
			sum += ratedRating.getRatingPoint();
		}
		return sum/ratingLst.size();
		
		
	}
}
