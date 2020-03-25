package com.flip.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.flip.entity.Product;
import com.flip.entity.ProductConfig;
import com.flip.entity.ProductPic;
import com.flip.entity.Rating;
import com.flip.event.ProductRatingEvent;
import com.flip.sharedObject.GeneralMenFootWearInfoSharedObject;
import com.flip.sharedObject.ProductPicSharedObject;
import com.flip.sharedObject.ProductSharedObject;


public interface ProductService {

	public Product addProduct(ProductSharedObject product,Long[] picIdLst);
	
	public Product getProductById(String id);
	
	public List<Product> getAllProduct();
	
	public Product updateProduct(String id,Product product) throws Exception;
	
	public Product deleteProductById(String id) throws Exception;
	
	public ProductPic addProductPic(MultipartFile file,ProductPicSharedObject picInfoSharedObject);
	
	public Product addProductPicLst(MultipartFile[] file,ProductPicSharedObject[] picInfoSharedObject,String productId);
	
	public Product addPicLstToProduct(Long[] picIdLst,String productId);
	
	public List<ProductConfig> getAllProductNames();
	
	public Product addGeneralMenFootWeerInfo(GeneralMenFootWearInfoSharedObject product,Product productEntity);
	
	public Product updateRating(ProductRatingEvent productRatingEvent);
	
}
