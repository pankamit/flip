package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaFlipServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaFlipServerServiceApplication.class, args);
		System.out.println("eureka server");
	}

}
