package com.flip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.flip.feignclient"})
public class FlipOrderManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlipOrderManageServiceApplication.class, args);
		System.out.println("Order Manage Service");
	}
	
}
