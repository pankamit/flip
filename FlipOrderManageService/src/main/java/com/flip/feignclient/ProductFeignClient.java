package com.flip.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.flip.entity.dto.Response;

@FeignClient(name = "product-service")
public interface ProductFeignClient {
	
	@GetMapping("product/{id}")
	public Response getProductById(@PathVariable("id") String id);
	
	@GetMapping("product")
	public Response getAllProduct();

}
