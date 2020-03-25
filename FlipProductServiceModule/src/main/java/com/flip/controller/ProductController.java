package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flip.entity.Product;
import com.flip.service.ProductService;
import com.flip.sharedObject.GeneralMenFootWearInfoSharedObject;
import com.flip.sharedObject.ProductPicSharedObject;
import com.flip.util.HelloServiceConfiguration;
import com.flip.util.Response;

@RestController
@RibbonClient(name = "hello-product-service", configuration = HelloServiceConfiguration.class)
//@RequestMapping("/product-service-config")
public class ProductController {


	@Autowired
	private ProductService productService;
	
//	@PostMapping(value = "product/add", consumes={"application/json"})
//	public Response addProduct(@RequestBody ProductSharedObject product) {
//		return Response.builder().data(productService.addProduct(product)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
//	}

	@GetMapping("product/{id}")
	public Response getProductById(@PathVariable("id") String id) {
		return Response.builder().data(productService.getProductById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("product")
	public Response getAllProduct(){
		return Response.builder().data(productService.getAllProduct()).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping("product/{id}")
	public Response updateProduct(@PathVariable("id")String id,@RequestBody Product product) throws Exception{
		return Response.builder().data(productService.updateProduct(id, product)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("product/{id}")
	public Response deleteProductById(@PathVariable("id")String id) throws Exception{
		return Response.builder().data(productService.deleteProductById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	};
	
	@PostMapping(value = "productPic",consumes = {"multipart/form-data"})
	public Response addProductPic(@RequestPart MultipartFile file,@RequestPart("picInfo") ProductPicSharedObject picInfo) {
		return Response.builder().data(productService.addProductPic(file, picInfo)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
//	@PostMapping(value = "productPicLst",consumes = {"multipart/form-data"})
//	public Response addProductPicLst(@RequestPart MultipartFile[] files,@RequestPart ProductPicSharedObject[] picInfoLst,@RequestPart String productId) {
//		System.out.println("Product Id : "+productId);
//		return Response.builder().data(productService.addProductPicLst(files, picInfoLst,productId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
//	}
	
	@PostMapping(value = "addpiclsttoproduct")
	public Response addPicLstToProduct(@RequestPart Long[] picIdLst,@RequestPart String productId) {
		System.out.println("Product Id : "+productId);
		return Response.builder().data(productService.addPicLstToProduct(picIdLst, productId)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("productNames")
	public Response getAllProductName(){
		return Response.builder().data(productService.getAllProductNames()).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
//	@PostMapping(value = "product/MENFOOT/add",consumes = {"multipart/form-data"})
//	public Response addProduct(@RequestPart MultipartFile[] files,@RequestPart("product") GeneralMenFootWearInfoSharedObject product) {
//		Product productEntity = productService.addProduct(product,files);
//		return Response.builder().data(productService.addGeneralMenFootWeerInfo(product,productEntity)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
//	}
	
	@PostMapping(value = "product/MENFOOT/add")
	public Response addProductGeneralMenFootWearInfo(@RequestPart Long[] picIdLst,@RequestPart("product") GeneralMenFootWearInfoSharedObject product) {
		Product productEntity = productService.addProduct(product,picIdLst);
		return Response.builder().data(productService.addGeneralMenFootWeerInfo(product,productEntity)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	
	@GetMapping("/helloProduct")
	public String helloUser() {
		return "hello user from Product Service";
	}
	
}
