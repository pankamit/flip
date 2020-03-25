package com.flip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flip.entity.SubCategory;
import com.flip.service.SubCategoryService;
import com.flip.sharedObject.SubCategorySharedObject;
import com.flip.util.Response;

@RestController
//@RequestMapping("/product-service-config")
public class SubCategoryController {


	@Autowired
	private SubCategoryService subCategoryService;
	
	@PostMapping("subCategory/add")
	public Response addSubCategory(@RequestBody SubCategorySharedObject subCategory) {
		return Response.builder().data(subCategoryService.addSubCategory(subCategory)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("subCategory/{id}")
	public Response getSubCategoryById(@PathVariable("id") Long id) {
		return Response.builder().data(subCategoryService.getSubCategoryById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("subCategory")
	public Response getAllSubCategory(){
		return Response.builder().data(subCategoryService.getAllSubCategory()).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping("subCategory/{id}")
	public Response updateSubCategory(@PathVariable("id")Long id,@RequestBody SubCategory subCategory) throws Exception{
		return Response.builder().data(subCategoryService.updateSubCategory(id, subCategory)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("subCategory/{id}")
	public Response deleteSubCategoryById(@PathVariable("id")Long id) throws Exception{
		return Response.builder().data(subCategoryService.deleteSubCategoryById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	};
}
