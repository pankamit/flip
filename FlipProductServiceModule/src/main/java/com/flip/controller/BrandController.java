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

import com.flip.entity.Brand;
import com.flip.service.BrandService;
import com.flip.sharedObject.BrandSharedObject;
import com.flip.util.Response;

@RestController
//@RequestMapping("/product-service-config")
public class BrandController {


	@Autowired
	private BrandService brandService;
	
	@PostMapping("brand/add")
	public Response addBrand(@RequestBody BrandSharedObject brand) {
		return Response.builder().data(brandService.addBrand(brand)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("brand/{id}")
	public Response getBrandById(@PathVariable("id") Long id) {
		return Response.builder().data(brandService.getBrandById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@GetMapping("brand")
	public Response getAllBrand(){
		return Response.builder().data(brandService.getAllBrand()).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@PutMapping("brand/{id}")
	public Response updateBrand(@PathVariable("id")Long id,@RequestBody Brand brand) throws Exception{
		return Response.builder().data(brandService.updateBrand(id, brand)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	}
	
	@DeleteMapping("brand/{id}")
	public Response deleteBrandById(@PathVariable("id")Long id) throws Exception{
		return Response.builder().data(brandService.deleteBrandById(id)).success(Boolean.TRUE).httpStatus(HttpStatus.OK).build();
	};
}
