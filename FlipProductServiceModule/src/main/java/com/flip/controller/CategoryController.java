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

import com.flip.entity.Category;
import com.flip.service.CategoryService;
import com.flip.sharedObject.CategorySharedObject;
import com.flip.util.Response;

@RestController
//@RequestMapping("/product-service-config")
public class CategoryController {


	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("category/add")
	public Response addCategory(@RequestBody CategorySharedObject category) {
		return Response.builder().data(categoryService.addCategory(category)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("category/{id}")
	public Response getCategoryById(@PathVariable("id") Long id) {
		return Response.builder().data(categoryService.getCategoryById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("category")
	public Response getAllCategory(){
		return Response.builder().data(categoryService.getAllCategory()).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping("category/{id}")
	public Response updateCategory(@PathVariable("id")Long id,@RequestBody Category category) throws Exception{
		return Response.builder().data(categoryService.updateCategory(id, category)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("category/{id}")
	public Response deleteCategoryById(@PathVariable("id")Long id) throws Exception{
		return Response.builder().data(categoryService.deleteCategoryById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	};
}
