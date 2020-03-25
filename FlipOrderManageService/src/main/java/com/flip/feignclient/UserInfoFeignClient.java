package com.flip.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.flip.entity.dto.Response;

@FeignClient(name = "user-info-service")
public interface UserInfoFeignClient {
	
	@GetMapping("/user/{userId}")
	public Response getUserById(@PathVariable("userId") String userId);
	
	@GetMapping("/helloUser")
	public Response helloUser();

}
