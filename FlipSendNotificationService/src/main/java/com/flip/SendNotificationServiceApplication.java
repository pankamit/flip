package com.flip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SendNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendNotificationServiceApplication.class, args);
		System.out.println("Notification Service");
	}

}
